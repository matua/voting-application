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
    public void addNewRestaurant(@RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);
        restaurantRepository.save(restaurant);
    }

    @PostMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addDishToRestaurant(@RequestBody DishDto dishDto, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));

        Dish dish = new Dish();
        dish.setDate(LocalDateTime.now().withNano(0));
        BeanUtils.copyProperties(dishDto, dish);
        restaurantToUpdate.addDishToRestaurant(dish);
        restaurantRepository.save(restaurantToUpdate);
    }

    @PostMapping(path = "{restaurantId}/dishes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addDishesToRestaurant(@RequestBody Set<DishDto> dishDtos, @PathVariable Integer restaurantId) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));

        Set<Dish> dishes = new HashSet<>();
        dishDtos.forEach(dishDto -> {
            Dish dish = new Dish();
            dish.setDate(LocalDateTime.now());
//            dish.setRestaurant();
            BeanUtils.copyProperties(dishDto, dish);
            dishes.add(dish);
        });

        restaurantToUpdate.addToMenu(dishes);
        restaurantRepository.save(restaurantToUpdate);
    }

    @GetMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Restaurant getRestaurantById(@PathVariable Integer restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + " was not found"));
    }

//    @GetMapping(path = "{restaurantName}")
//    public Restaurant getRestaurantByName(@PathVariable String restaurantName) {
//        return restaurantRepository.findByName(restaurantName)
//                .orElseThrow(() -> new EntityNotFoundException(
//                        "Restaurant: " + restaurantName + " was not found"));
//    }

    @GetMapping("{offset}/{limit}")
    @ResponseBody
    public EntitiesPageDto<Restaurant> getAllRestaurants(@PathVariable Integer offset,
                                                         @PathVariable Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return new EntitiesPageDto<>(restaurantRepository.findAll(nextPage).getContent());
    }

    @PutMapping(path = "{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateRestaurant(@PathVariable Integer restaurantId, @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToUpdate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant with id " + restaurantId + "was not found"));
        restaurantToUpdate.setName(restaurantDto.getName());
        restaurantRepository.save(restaurantToUpdate);
    }

    @DeleteMapping(path = "{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteRestaurantById(@PathVariable Integer restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}