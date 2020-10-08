package com.github.health.check.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class UserDTO {

    @ApiModelProperty(value = "用户名")
    @NotEmpty
    private String username;

    @ApiModelProperty(value = "密码")
    @NotEmpty
    private String password;

}
