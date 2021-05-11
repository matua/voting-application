package com.matuageorge.votingapplication.model;

import lombok.Data;

@Data
public class Dish {
    private Integer id;
    private String name;
    private Integer price; //price in cents US
}
