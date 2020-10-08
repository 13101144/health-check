package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.Channel;
import com.github.health.check.domain.entity.User;
import com.github.health.check.mapper.ChannelMapper;
import com.github.health.check.service.ChannelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

    @Resource
    private ChannelMapper channelMapper;

    @Override
    public List<Channel> getChannel(String projectName, String checkName) {
        QueryWrapper<Channel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name",projectName).and(wrapper ->wrapper.eq("check_name",checkName));
        List<Channel> channelList = channelMapper.selectList(queryWrapper);
        return channelList;
    }
}
