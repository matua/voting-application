package com.matuageorge.votingapplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "menu")
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractBaseEntity{
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
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
