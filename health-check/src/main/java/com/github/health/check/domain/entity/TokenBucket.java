package com.github.health.check.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TokenBucket {

    private String value;

    private String token;

    private Date updated;
}
