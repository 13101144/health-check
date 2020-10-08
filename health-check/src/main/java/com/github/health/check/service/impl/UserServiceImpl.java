package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.User;
import com.github.health.check.mapper.UserMapper;
import com.github.health.check.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Integer saveUser(User user) {

        user.setCreated(new Date());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.insert(user);
    }

    @Override
    public Boolean updateUser(User user) {
        user.setUpdated(new Date());
        return this.updateById(user);
    }

    @Override
    public IPage getUserPage(int page, String name) {
        Page<User> userPage = new Page<>(page,20);
        if (!StringUtils.isEmpty(name)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",name);
            return userMapper.selectPage(userPage, queryWrapper);
        }

        return userMapper.selectPage(userPage, null);
    }
}
