package com.github.health.check.domain.dto;

import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Data
public class CheckDto {

    private long id;

    private String tags;

    private String code;

    private String description;

    private Date startTime;

    private Duration grace;

    private String schedule;

    private String timeZone;

    private String subject;
}
