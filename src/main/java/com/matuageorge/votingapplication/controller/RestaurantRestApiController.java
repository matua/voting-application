package com.matuageorge.votingapplication.controller;

import com.matuageorge.votingapplication.dto.DishDto;
import com.matuageorge.votingapplication.dto.RestaurantDto;
import com.matuageorge.votingapplication.dto.RestaurantsPageDto;
import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.model.Restaurant;
import com.matuageorge.votingapplication.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/voting/restaurants/")
public class RestaurantRestApiController {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantRestApiController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public void addNewRestaurant(@RequestBody Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @PostMapping(path = "{restaurantId}")
    public void addDishToRestaurant(@RequestBody DishDto dishDto, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));

        Dish dish = new Dish();
        dish.setDate(LocalDateTime.now());
        BeanUtils.copyProperties(dishDto, dish);
        restaurantToUpdate.addDishToRestaurant(dish);
        restaurantRepository.save(restaurantToUpdate);
    }

    @GetMapping(path = "{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Integer restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));
    }

    @GetMapping("{offset}/{limit}")
    @ResponseBody
    public RestaurantsPageDto getAllRestaurants(@PathVariable Integer offset,
                                                @PathVariable Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return new RestaurantsPageDto(restaurantRepository.findAll(nextPage).getContent());
    }

    @PutMapping(path = "{restaurantId}")
    public void updateRestaurant(@PathVariable Integer restaurantId, @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + "was not found"));
        restaurantToUpdate.setName(restaurantDto.getName());
        restaurantRepository.save(restaurantToUpdate);
    }

    @DeleteMapping(path = "{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurantById(@PathVariable Integer restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}