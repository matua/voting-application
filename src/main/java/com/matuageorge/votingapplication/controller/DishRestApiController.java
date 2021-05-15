package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.repository.DishRepository;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/voting/dishes/")
public class DishRestApiController {

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
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));
        return new ResponseEntity<>(dishRepository.findByRestaurantId(restaurantId), HttpStatus.OK);
    }

//    @GetMapping(path = "{restaurantName}")
//    public ResponseEntity<List<Dish>> getDishesByRestaurantName(@PathVariable String restaurantName) {
//        restaurantRepository.findByName(restaurantName)
//                .orElseThrow(() -> new EntityNotFoundException(
//                        "Restaurant \"" + restaurantName + "\" was not found"));
//        Integer restaurantId = restaurantRepository.findByName(restaurantName).get().getId();
//        return new ResponseEntity<>(dishRepository.findByRestaurantId(restaurantId), HttpStatus.OK);
//    }


    @DeleteMapping(path = "{dishId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteDishById(@PathVariable Integer dishId) {
        dishRepository.deleteById(dishId);
        return new ResponseEntity<>("Dish was deleted", HttpStatus.OK);
    }

    @PutMapping(path = "{dishId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateDish(@PathVariable Integer dishId, @RequestBody DishDto dishDto) {
        Dish dishToUpdate = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Dish with id " + dishId + " was not found"));
        BeanUtils.copyProperties(dishDto, dishToUpdate);
        dishRepository.save(dishToUpdate);
        return new ResponseEntity<>("Dish was successfully updated", HttpStatus.OK);
    }
}