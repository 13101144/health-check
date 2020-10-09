package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.Notification;
import com.github.health.check.mapper.NotificationMapper;
import com.github.health.check.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

    @Resource
    NotificationMapper notificationMapper;

    @Override
    public IPage getNotificationPage(int page, String name) {
        Page<Notification> noticePage = new Page<>(page,20);
        if (!StringUtils.isEmpty(name)) {
            QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            return notificationMapper.selectPage(noticePage, queryWrapper);
        }
        return notificationMapper.selectPage(noticePage, null);
    }

    @Override
    public List<Notification> getUnHandleNotification() {
        QueryWrapper queryWrapper = new QueryWrapper<>().eq("status","N");
        List<Notification> notificationList = notificationMapper.selectList(queryWrapper);
        return notificationList;
    }
}
