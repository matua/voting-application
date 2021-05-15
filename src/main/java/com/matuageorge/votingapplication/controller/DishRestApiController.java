package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.repository.DishRepository;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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
    public HttpEntity<?> getDishesByRestaurantId(@PathVariable Integer restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)){
            return new ResponseEntity<>(
                    "Restaurant not found",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dishRepository.findByRestaurantId(restaurantId), HttpStatus.OK);
    }


    @DeleteMapping(path = "{restaurantId}/{dishId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteDishById(@PathVariable Integer restaurantId,
                               @PathVariable Integer dishId) {
        dishRepository.deleteById(dishId);
    }

    @PutMapping(path = "{dishId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateDish(@PathVariable Integer dishId, @RequestBody DishDto dishDto) {
        Dish dishToUpdate = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Dish with id " + dishId + "was not found"));
        dishToUpdate.setName(dishDto.getName());
        dishRepository.save(dishToUpdate);
    }
}