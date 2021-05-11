package com.matuageorge.votingapplication.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Vote {
    private Integer id;
    private User user;
    private Restaurant restaurant;
    private Date votingDate;
}
