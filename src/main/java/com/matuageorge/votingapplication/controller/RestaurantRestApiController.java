package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.dto.EntitiesPageDto;
import com.matuageorge.votingapplication.dto.RestaurantDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.model.Restaurant;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/v1/voting/restaurants/")
public class RestaurantRestApiController {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantRestApiController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addNewRestaurant(@RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);
        restaurantRepository.save(restaurant);
        return new ResponseEntity<>("Restaurant was added successfully", HttpStatus.OK);
    }

    @PostMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addDishToRestaurant(@RequestBody DishDto dishDto, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));

        Dish dish = new Dish();
        dish.setDate(LocalDateTime.now().withNano(0));
        BeanUtils.copyProperties(dishDto, dish);
        restaurantToUpdate.addDishToRestaurant(dish);
        restaurantRepository.save(restaurantToUpdate);
        return new ResponseEntity<>("The dish was added successfully", HttpStatus.OK);
    }

    @PostMapping(path = "{restaurantId}/dishes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addDishesToRestaurant(@RequestBody Set<DishDto> dishDtos, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));

        Set<Dish> dishes = new HashSet<>();
        dishDtos.forEach(dishDto -> {
            Dish dish = new Dish();
            dish.setDate(LocalDateTime.now());
            BeanUtils.copyProperties(dishDto, dish);
            dishes.add(dish);
        });

        restaurantToUpdate.addToMenu(dishes);
        restaurantRepository.save(restaurantToUpdate);
        return new ResponseEntity<>("The dishes were added successfully", HttpStatus.OK);
    }

    @GetMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer restaurantId) {
        return new ResponseEntity<>(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found")), HttpStatus.OK);
    }

    @GetMapping(path = "{restaurantName}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String restaurantName) {
        return new ResponseEntity<>(restaurantRepository.findByName(restaurantName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant: " + restaurantName + " was not found")), HttpStatus.OK);
    }

    @GetMapping("{offset}/{limit}")
    @ResponseBody
    public EntitiesPageDto<Restaurant> getAllRestaurants(@PathVariable Integer offset,
                                                         @PathVariable Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return new EntitiesPageDto<>(restaurantRepository.findAll(nextPage).getContent());
    }

    @PutMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateRestaurant(@PathVariable Integer restaurantId, @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + "was not found"));
        restaurantToUpdate.setName(restaurantDto.getName());
        restaurantRepository.save(restaurantToUpdate);
        return new ResponseEntity<>("Restaurant was successfully updated", HttpStatus.OK);
    }

    @DeleteMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Integer restaurantId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + "was not found"));
        restaurantRepository.deleteById(restaurantId);
        return new ResponseEntity<>("Restaurant was deleted", HttpStatus.OK);
    }
}