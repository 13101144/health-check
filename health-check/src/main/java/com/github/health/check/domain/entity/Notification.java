package com.github.health.check.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {

    private Long id;

    private String projectName;

    private String checkName;

    private String to;

    private String method;

    private String error;

    private String status;

    private Date created;
}
