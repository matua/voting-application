package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Dish;
import com.matuageorge.votingapplication.model.Restaurant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//https://stackoverflow.com/questions/10394857/how-to-use-transactional-with-spring-data
@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Cacheable("Dishes by Restaurant")
    List<Dish> findByRestaurant(Restaurant restaurant);

    Optional<Dish> findByName(String dishName);
}