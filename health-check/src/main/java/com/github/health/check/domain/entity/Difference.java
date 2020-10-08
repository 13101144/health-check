package com.github.health.check.domain.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description="两次任务之间执行时间的差值")
public class Difference {
    private int id;

    private String jobName;

    private String jobGroup;

    private long dValue;
}
