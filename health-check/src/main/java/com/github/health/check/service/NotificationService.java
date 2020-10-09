package com.github.health.check.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.Notification;

import java.util.List;


public interface NotificationService extends IService<Notification> {

    IPage getNotificationPage(int page, String name);

    List<Notification> getUnHandleNotification();

}
