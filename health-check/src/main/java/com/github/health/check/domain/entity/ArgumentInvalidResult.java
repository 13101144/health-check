package com.github.health.check.domain.entity;

import lombok.Data;

@Data
public class ArgumentInvalidResult {

    private String field;

    private Object rejectedValue;

    private String defaultMessage;
}
