package com.github.health.check.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.health.check.constant.CommonConstants;
import com.github.health.check.domain.dto.UserDTO;
import com.github.health.check.domain.entity.User;
import com.github.health.check.domain.vo.UserVo;
import com.github.health.check.enums.ErrorCode;
import com.github.health.check.exception.BusinessException;
import com.github.health.check.service.UserService;
import com.github.health.check.util.JwtUtil;
import com.github.health.check.util.R;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

//    @PostMapping("/v1/login")
//    public R login(@RequestBody UserDTO userDTO) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",userDTO.getUsername()).and(wrapper ->wrapper.eq("password",userDTO.getPassword()));
//        User user = userService.getOne(queryWrapper);
//        if (user == null) {
//            throw new BusinessException(ErrorCode.INVALID_USERNAME_OR_PASSWOD.getCode(),ErrorCode.INVALID_USERNAME_OR_PASSWOD.getMsg());
//        }
//        // 生成token start
//        String token = JwtUtil.createToken(user);
//        UserVo userVo = new UserVo();
//        userVo.setUser(user);
//        userVo.setToken(token);
//        return R.success(userVo);
//    }

}
