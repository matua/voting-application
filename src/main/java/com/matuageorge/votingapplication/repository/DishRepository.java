package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query(value = "select * from dishes d " +
            "where d.restaurant_id = :restaurantId", nativeQuery = true)
    List<Dish> findByRestaurantId(Integer restaurantId);
}
