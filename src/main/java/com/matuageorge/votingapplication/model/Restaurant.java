package com.matuageorge.votingapplication.model;

import lombok.Data;

import java.util.Set;

@Data
public class Restaurant {
    private Integer id;
    private Set<Dish> menu;
}
