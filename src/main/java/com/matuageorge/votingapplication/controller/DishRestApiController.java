package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.model.Restaurant;
import com.matuageorge.votingapplication.repository.DishRepository;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("dishes")
public class DishRestApiController {

    private static final Logger logger = LoggerFactory.getLogger(DishRestApiController.class);
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishRestApiController(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Dish>> getDishesByRestaurantId(@PathVariable Integer restaurantId) {
        logger.info("Getting dish by id:{}", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));
        return new ResponseEntity<>(dishRepository.findByRestaurant(restaurant), HttpStatus.OK);
    }

    @PutMapping(path = "{dishId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateDish(@PathVariable Integer dishId, @RequestBody DishDto dishDto) {
        logger.info("Checking if dish with id:{} exists", dishId);
        Dish dishToUpdate = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Dish with id " + dishId + " was not found"));
        BeanUtils.copyProperties(dishDto, dishToUpdate);
        logger.info("Persisting the updated dish with id:{} to database", dishId);
        dishRepository.save(dishToUpdate);
        return new ResponseEntity<>("Dish was successfully updated", HttpStatus.OK);
    }

    @DeleteMapping(path = "{dishId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteDishById(@PathVariable Integer dishId) {
        logger.info("Checking if dish with id:{} exists", dishId);
        dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Dish with id " + dishId + " was not found"));
        logger.info("Deleting dish with id:{} to database", dishId);
        dishRepository.deleteById(dishId);
        return new ResponseEntity<>("Dish was deleted", HttpStatus.OK);
    }
}