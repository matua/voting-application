package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.VoteDto;
import com.matuageorge.votingapplication.model.Restaurant;
import com.matuageorge.votingapplication.model.User;
import com.matuageorge.votingapplication.model.Vote;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import com.matuageorge.votingapplication.repository.UserRepository;
import com.matuageorge.votingapplication.repository.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/voting/votes/")
public class VoteRestApiController {

    public static final LocalDateTime VOTING_TIME_THRESHOLD = LocalDate.now().atTime(17, 0);
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteRestApiController(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto,
                                       Principal voter) {
        LocalDateTime votingDateTime = LocalDateTime.now().withNano(0);
        LocalDate votingDate = votingDateTime.toLocalDate();
        LocalTime votingTime = votingDateTime.toLocalTime();

        if (votingDateTime.isBefore(VOTING_TIME_THRESHOLD)) {
            User user = userRepository.findByEmail(voter.getName())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User email: " + voter.getName() + " was not found"));
            Restaurant restaurant = restaurantRepository.findByName(voteDto.getRestaurantName())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Restaurant: " + voteDto.getRestaurantName() + " was not found"));
            Vote vote = new Vote()
                    .setRestaurant(restaurant)
                    .setUser(user)
                    .setVotingDate(votingDate)
                    .setVotingTime(votingTime);
            BeanUtils.copyProperties(voteDto, vote);
            if (voteRepository.existsByUserIdAndVotingDate(user.getId(), votingDate)) {
                vote = voteRepository.findByUserIdAndVotingDate(user.getId(), votingDate);
                BeanUtils.copyProperties(voteDto, vote);
                voteRepository.save(vote);
                return new ResponseEntity<>("Your vote is updated", HttpStatus.OK);
            }
            voteRepository.save(vote);
            return new ResponseEntity<>("Your vote is taken", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Voting time is over", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/results/{date}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<Restaurant, List<Vote>> getVotingResultsDetailedByDate(@PathVariable(value = "date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        List<Vote> votes = voteRepository.findAllByVotingDate(LocalDate.parse(date, formatter));
        if (votes.isEmpty()) {
            throw new RuntimeException("No results found for this date");
        }
        return votes.stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant));
    }

    @GetMapping(path = "/simple-results/{date}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Map<Restaurant, Long> getSimpleVotingResults(@PathVariable(value = "date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        List<Vote> votes = voteRepository.findAllByVotingDate(LocalDate.parse(date, formatter));
        Map<Restaurant, Long> result = votes.stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));

        result
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    @DeleteMapping(path = "{voteId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Integer voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Vote: " + voteId + " was not found"));
        voteRepository.deleteById(vote.getId());
        return new ResponseEntity<>("Vote was deleted successfully", HttpStatus.OK);
    }
}