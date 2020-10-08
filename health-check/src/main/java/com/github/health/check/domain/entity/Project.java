package com.github.health.check.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@ApiModel(description="项目信息")
public class Project {

    @ApiModelProperty("项目id")
    private int id;

    @NotEmpty
    @ApiModelProperty("项目名")
    private String name;

    @ApiModelProperty("项目代码")
    private String code;

    @NotEmpty
    @ApiModelProperty("项目拥有者")
    private String owner;

    @ApiModelProperty("项目描述")
    private String description;

    @ApiModelProperty("创建时间")
    private LocalDateTime created;

    @ApiModelProperty("更新时间")
    private LocalDateTime updated;

}
