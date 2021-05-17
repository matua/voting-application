package com.matuageorge.votingapplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "no_duplicate_name_idx")})
@Entity
public class Dish extends AbstractNamedEntity {
    @Column(nullable = false)
    @Range(min = 100, max = 10000)
    private Integer price; //price in cents US
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;
    @NotNull
    private LocalDateTime date;
}