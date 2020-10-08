package com.github.health.check.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(description="检查项信息")
public class CheckItem {

    @ApiModelProperty("检查项id")
    @TableId(type= IdType.AUTO)
    private Long id;

    @NotEmpty
    @ApiModelProperty("检查项名字")
    private String name;

    @ApiModelProperty("检查项标签")
    private String tags;

    @ApiModelProperty("检查项代码")
    private String code;

    @ApiModelProperty("检查项描述")
    private String description;

    @NotEmpty
    @ApiModelProperty("工程名")
    private String projectName;

    @NotNull
    @ApiModelProperty("容忍周期")
    private Integer grace;

    @NotNull
    @ApiModelProperty("容忍周期,单位分钟")
    private Integer maxExecTime;

    @NotEmpty
    @ApiModelProperty("cron表达式调度周期")
    private String schedule;

    @ApiModelProperty("时区")
    private String timeZone;

    @ApiModelProperty("主题")
    private String subject;

    @ApiModelProperty("创建时间")
    private LocalDateTime created;

    @ApiModelProperty("更新时间")
    private LocalDateTime updated;

}
