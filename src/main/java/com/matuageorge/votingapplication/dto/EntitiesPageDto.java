package com.matuageorge.votingapplication.dto;

import lombok.Data;

import java.util.List;

@Data
public class EntitiesPageDto<T> {
    private Integer count;
    private List<T> entities;

    public EntitiesPageDto(List<T> entities) {
        this.entities = entities;
        this.count = entities.size();
    }
}
