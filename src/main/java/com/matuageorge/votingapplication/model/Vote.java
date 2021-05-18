package com.matuageorge.votingapplication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "voting_date"}, name = "UQ_Votes_User_id_Voting_date")})
public class Vote extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id" )
    @NotNull
    private Restaurant restaurant;
    @NotNull
    @Column(name = "voting_date", nullable = false)
    private LocalDate votingDate;
    @NotNull
    @Column(nullable = false)
    private LocalTime votingTime;

}
