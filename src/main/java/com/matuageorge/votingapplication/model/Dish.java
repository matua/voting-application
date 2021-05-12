package com.matuageorge.votingapplication.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "dishes")
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price; //price in cents US
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    private LocalDateTime date;
}