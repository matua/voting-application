package com.matuageorge.votingapplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "menu")
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
    @ToString.Exclude
    @JsonManagedReference
    private Set<Dish> menu;

    public void addDishToRestaurant(Dish dish){
        menu.add(dish);
    }

    public void addToMenu(Set<Dish> dishes) {
        menu.addAll(dishes);
    }
}
