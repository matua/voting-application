package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Page<Restaurant> findAll(Pageable page);
}