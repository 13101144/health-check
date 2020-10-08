package com.github.health.check.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@ApiModel(description="开关")
public class Flip {

    @ApiModelProperty("开关id")
    @TableId(type= IdType.AUTO)
    private Long id;

    @NotEmpty
    @ApiModelProperty("项目名")
    private String projectName;

    @NotEmpty
    @ApiModelProperty("检查项名")
    private String checkName;

    @ApiModelProperty("创建时间")
    private Date created;

    @ApiModelProperty("处理时间")
    private Date processed;

    @ApiModelProperty("状态")
    private String status;

    private String content;

}
