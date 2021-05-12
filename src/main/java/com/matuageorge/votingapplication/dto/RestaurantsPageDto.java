package com.matuageorge.votingapplication.dto;

import com.matuageorge.votingapplication.model.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantsPageDto {
    private Integer count;
    private List<Restaurant> restaurants;

    public RestaurantsPageDto(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        this.count = restaurants.size();
    }
}
