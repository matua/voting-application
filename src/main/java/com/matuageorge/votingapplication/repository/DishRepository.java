package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
