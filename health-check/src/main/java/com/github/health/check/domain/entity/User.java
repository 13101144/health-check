package com.github.health.check.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.omg.CORBA.IDLType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@ApiModel(description="用户信息")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotEmpty
    private String username;

    private String password;

    private String nickname;

    private String role;

    private String phone;

    @Email
    private String email;

    private int sex;

    private String enabled;

    private String type;

    private Date created;

    private Date updated;

}
