package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.health.check.domain.entity.JwtUser;
import com.github.health.check.domain.entity.User;
import com.github.health.check.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
        return new JwtUser(user);
    }
}
