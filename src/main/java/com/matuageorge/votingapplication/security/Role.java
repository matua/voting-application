package com.matuageorge.votingapplication.security;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public enum Role {
    ADMIN,
    USER;

    @Id
    @GeneratedValue
    private Integer id;
}
