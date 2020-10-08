package com.github.health.check.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Member {

    private Long userId;

    private Long projectId;

    private Date transferRequestDate;

    private boolean rw;

}
