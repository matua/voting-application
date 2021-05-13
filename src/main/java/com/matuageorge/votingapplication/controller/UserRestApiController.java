package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.EntitiesPageDto;
import com.matuageorge.votingapplication.dto.UserDto;
import com.matuageorge.votingapplication.exceptions.VotingException;
import com.matuageorge.votingapplication.model.User;
import com.matuageorge.votingapplication.repository.UserRepository;
import com.matuageorge.votingapplication.security.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("api/v1/voting/users/")
public class UserRestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserRestApiController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public void addNewUser(@RequestBody UserDto userDto) {
        User user = new User()
                .setActivated(false)
                .setRoles(Set.of(Role.USER))
                .setEmail(userDto.getEmail())
                .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .setRegistrationDate(LocalDateTime.now().withNano(0));
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user);
        } else {
            throw new VotingException("User already exists");
        }
    }

    @GetMapping(path = "{userEmail}")
    public User getUserByEmail(@PathVariable String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User email: " + userEmail + " was not found"));
    }

    @GetMapping("{offset}/{limit}")
    @ResponseBody
    public EntitiesPageDto<User> getAllUsers(@PathVariable Integer offset,
                                             @PathVariable Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return new EntitiesPageDto<>(userRepository.findAll(nextPage).getContent());
    }

    @PutMapping(path = "{userEmail}")
    public void updateUser(@PathVariable String userEmail, @RequestBody UserDto userDto) {
        User userToUpdate = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User email: " + userEmail + " was not found"));
        BeanUtils.copyProperties(userDto, userToUpdate);
        userRepository.save(userToUpdate);
    }

    @DeleteMapping(path = "{userEmail}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByEmail(@PathVariable String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User email: " + userEmail + " was not found"));
        userRepository.deleteById(user.getId());
    }
}