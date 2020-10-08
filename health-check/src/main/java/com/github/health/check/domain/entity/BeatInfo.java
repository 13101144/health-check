package com.github.health.check.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description="心跳信息")
public class BeatInfo {

    @ApiModelProperty("心跳id")
    private Long id;

    @NotEmpty
    @ApiModelProperty("心跳名")
    private String name;

    @NotEmpty
    @ApiModelProperty("心跳编号")
    private String code;

    @NotEmpty
    @ApiModelProperty("心跳所属应用")
    private String owner;

    @NotEmpty
    @ApiModelProperty("心跳类型")
    private String type;

    @NotEmpty
    @ApiModelProperty("发送心跳的服务器地址")
    private String address;

    @ApiModelProperty("定时任务方法名")
    private String methodName;

    @NotEmpty
    @ApiModelProperty("定时任务名")
    private String serviceName;

    @ApiModelProperty("元信息")
    private String metaData;

    @ApiModelProperty("心跳是否处理")
    private String status;

    @NotNull
    @ApiModelProperty("发送时间")
    private Date created;

    @NotNull
    @ApiModelProperty("任务开始时间")
    private Date beginTime;

    @NotNull
    @ApiModelProperty("任务结束时间")
    private Date endTime;

}
