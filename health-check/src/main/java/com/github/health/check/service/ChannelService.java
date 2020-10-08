package com.github.health.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.Channel;

import java.util.List;


public interface ChannelService extends IService<Channel> {

    List<Channel> getChannel(String projectName, String checkName);
}
