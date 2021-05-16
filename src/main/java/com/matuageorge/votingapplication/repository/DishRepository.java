package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.model.Restaurant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//https://stackoverflow.com/questions/10394857/how-to-use-transactional-with-spring-data
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Cacheable("Dishes by Restaurant")
    List<Dish> findByRestaurant(Restaurant restaurant);
}