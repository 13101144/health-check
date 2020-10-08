package com.github.health.check.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Channel {

    private Integer id;

    private String name;

    private String code;

    private String projectName;

    private Date created;

    private String kind;

    private String value;

    private String lastError;
}
