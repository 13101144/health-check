package com.github.health.check.domain.vo;

import com.github.health.check.domain.entity.JwtUser;
import com.github.health.check.domain.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private JwtUser user;

    private String token;
}
