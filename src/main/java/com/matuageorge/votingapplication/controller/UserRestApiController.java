package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.EntitiesPageDto;
import com.matuageorge.votingapplication.dto.UserDto;
import com.matuageorge.votingapplication.model.Role;
import com.matuageorge.votingapplication.model.User;
import com.matuageorge.votingapplication.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addNewUser(@RequestBody UserDto userDto) {
        User user = new User()
                .setActivated(false)
                .setRoles(Set.of(new Role("ADMIN")))
                .setEmail(userDto.getEmail())
                .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .setRegistrationDate(LocalDateTime.now().withNano(0));
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("User already exists");
        }
        return new ResponseEntity<>("User was added successfully", HttpStatus.OK);
    }

    @GetMapping(path = "{userEmail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User email: " + userEmail + " was not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("{offset}/{limit}")
    public EntitiesPageDto<User> getAllUsers(@PathVariable Integer offset,
                                             @PathVariable Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return new EntitiesPageDto<>(userRepository.findAll(nextPage).getContent());
    }

    @PutMapping(path = "{userEmail}")
    public ResponseEntity<String> updateUser(@PathVariable String userEmail, @RequestBody UserDto userDto) {
        User userToUpdate = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User email: " + userEmail + " was not found"));
        BeanUtils.copyProperties(userDto, userToUpdate);
        userRepository.save(userToUpdate);
        return new ResponseEntity<>("User was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "{userEmail}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User email: " + userEmail + " was not found"));
        userRepository.deleteById(user.getId());
        return new ResponseEntity<>("User was deleted successfully", HttpStatus.OK);
    }
}