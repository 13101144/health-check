package com.github.health.check.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@ApiModel(description="检查项结果信息")
public class CheckResult {

    @ApiModelProperty("检查项结果id")
    @TableId(type= IdType.AUTO)
    private long id;

    @ApiModelProperty("心跳信息id")
    private long beatId;

    @ApiModelProperty("项目名")
    @NotEmpty
    private String projectName;

    @ApiModelProperty("检查项名")
    @NotEmpty
    private String checkName;

    @ApiModelProperty("心跳创建时间")
    private Date beatCreated;

    @ApiModelProperty("心跳服务器地址")
    private String address;

    @ApiModelProperty("Y表示收到心跳，N表示未收到心跳")
    private String status;

    @ApiModelProperty("创建时间")
    private Date created;

}
