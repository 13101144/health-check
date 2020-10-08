package com.github.health.check.domain.entity;

import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Data
public class Profile {

    private Long userId;

    private Date nextReportDate;

    private boolean repoertAllowed;

    private Duration nagPeriod;

    private Date nextNagDate;

    private int pingLogLimit;

    private int checkLimit;

    private String token;

    private Date lastSmsDate;

    private Integer smsLimit;

    private Integer smsSent;

    private Date lastCallDate;

    private Integer callLimnit;

    private Integer teamLimnit;

    private Date deletionNoticeDate;

    private Date lastActiveDate;


}
