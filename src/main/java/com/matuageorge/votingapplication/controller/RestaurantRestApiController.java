package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.dto.EntitiesPageDto;
import com.matuageorge.votingapplication.dto.RestaurantDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.model.Restaurant;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("restaurants")
public class RestaurantRestApiController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantRestApiController.class);
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantRestApiController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addNewRestaurant(@RequestBody RestaurantDto restaurantDto) throws ConstraintViolationException {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);
        try {
            logger.info("Persisting restaurant \"{}\" to database", restaurant.getName());
            restaurantRepository.save(restaurant);
        } catch (Exception e) {
            return new ResponseEntity<>("Restaurant already exists", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Restaurant was added successfully", HttpStatus.OK);
    }

    @PostMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addDishToRestaurant(@RequestBody DishDto dishDto, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = checkIfRestaurantExists(restaurantId);
        Dish dish = new Dish();
        dish.setDate(LocalDateTime.now().withNano(0));
        BeanUtils.copyProperties(dishDto, dish);
        restaurantToUpdate.addDishToRestaurant(dish);
        logger.info("Persisting dish \"{}\" to database", dish.getName());
        restaurantRepository.save(restaurantToUpdate);
        return new ResponseEntity<>("The dish was added successfully", HttpStatus.OK);
    }

    @PostMapping(path = "{restaurantId}/dishes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addDishesToRestaurant(@RequestBody Set<DishDto> dishDtos, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = checkIfRestaurantExists(restaurantId);
        Set<Dish> dishes = new HashSet<>();
        dishDtos.forEach(dishDto -> {
            Dish dish = new Dish();
            dish.setDate(LocalDateTime.now());
            BeanUtils.copyProperties(dishDto, dish);
            logger.info("Persisting dish \"{}\" to database", dish.getName());
            dishes.add(dish);
        });
        restaurantToUpdate.addToMenu(dishes);
        logger.info("Persisting restaurant \"{}\" to database", restaurantToUpdate.getName());
        restaurantRepository.save(restaurantToUpdate);
        return new ResponseEntity<>("The dishes were added successfully", HttpStatus.OK);
    }

    @GetMapping(path = "/search-by-id/{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer restaurantId) {
        return new ResponseEntity<>(checkIfRestaurantExists(restaurantId), HttpStatus.OK);
    }

    @GetMapping(path = "/search-by-name/{restaurantName}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String restaurantName) {
        logger.info("Checking if restaurant \"{}\" exists", restaurantName);
        return new ResponseEntity<>(restaurantRepository.findByName(restaurantName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant: " + restaurantName + " was not found")), HttpStatus.OK);
    }

    @GetMapping("{offset}/{limit}")
    public EntitiesPageDto<Restaurant> getAllRestaurants(@PathVariable Integer offset,
                                                         @PathVariable Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        logger.info("Getting page:{} of size:{} of all restaurants", offset + 1, limit);
        return new EntitiesPageDto<>(restaurantRepository.findAll(nextPage).getContent());
    }

    @PutMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateRestaurant(@PathVariable Integer restaurantId, @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToUpdate = checkIfRestaurantExists(restaurantId);
        restaurantToUpdate.setName(restaurantDto.getName());
        logger.info("Persisting updated restaurant \"{}\" to database", restaurantToUpdate.getName());
        restaurantRepository.save(restaurantToUpdate);
        return new ResponseEntity<>("Restaurant was successfully updated", HttpStatus.OK);
    }

    @DeleteMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Integer restaurantId) {
        checkIfRestaurantExists(restaurantId);
        logger.info("Deleting restaurant with id:{}", restaurantId);
        restaurantRepository.deleteById(restaurantId);
        return new ResponseEntity<>("Restaurant was deleted", HttpStatus.OK);
    }

    private Restaurant checkIfRestaurantExists(Integer restaurantId) {
        logger.info("Checking if restaurant with id:{} exists", restaurantId);
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));
    }
}