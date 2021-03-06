package com.matuageorge.votingapplication.repository;

import com.matuageorge.votingapplication.model.Restaurant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Cacheable("Restaurant Page")
    Page<Restaurant> findAll(Pageable page);

    /*Note that Java8's Optional return types are automatically
     handled and its content is stored in the cache
     if present (Spring docs on @Cacheable)*/
    Optional<Restaurant> findByName(String restaurantName);
}