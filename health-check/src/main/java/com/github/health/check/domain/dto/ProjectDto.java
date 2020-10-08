package com.github.health.check.domain.dto;

import lombok.Data;

@Data
public class ProjectDto {

    private long id;

    private String owner;

    private String description;
}
