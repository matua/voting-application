package com.matuageorge.votingapplication.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Role extends AbstractNamedEntity {
    public Role(String name) {
        this.name = name;
    }
}
