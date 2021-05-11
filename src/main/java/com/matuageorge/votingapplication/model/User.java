package com.matuageorge.votingapplication.model;

import com.matuageorge.votingapplication.security.Role;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Data
public class User {
    private Integer id;
    private String email;
    private String password;
    private Date registrationDate;
    private Boolean activated;
    private Set<Role> roles;
}
