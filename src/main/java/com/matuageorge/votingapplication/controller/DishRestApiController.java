package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/voting/dishes/")
public class DishRestApiController {

    private final DishRepository dishRepository;

    @Autowired
    public DishRestApiController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping(path = "{restaurantId}")
    public List<Dish> getDishesByRestaurantId(@PathVariable Integer restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId);
    }


    @DeleteMapping(path = "{restaurantId}/{dishId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishById(@PathVariable Integer restaurantId,
                               @PathVariable Integer dishId) {
        dishRepository.deleteById(dishId);
    }

    @PutMapping(path = "{dishId}")
    public void updateDish(@PathVariable Integer dishId, @RequestBody DishDto dishDto) {
        Dish dishToUpdate = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Dish with id " + dishId + "was not found"));
        dishToUpdate.setName(dishDto.getName());
        dishRepository.save(dishToUpdate);
    }
}