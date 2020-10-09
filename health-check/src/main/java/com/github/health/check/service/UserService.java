package com.github.health.check.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.User;

public interface UserService extends IService<User> {

    Integer saveUser(User user);

    Boolean updateUser(User user);

    public IPage getUserPage(int page, String name);

}
