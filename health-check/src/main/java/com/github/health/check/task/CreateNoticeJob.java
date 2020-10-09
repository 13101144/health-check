package com.github.health.check.task;

import com.github.health.check.domain.entity.Channel;
import com.github.health.check.domain.entity.Flip;
import com.github.health.check.domain.entity.Notification;
import com.github.health.check.service.ChannelService;
import com.github.health.check.service.FlipService;
import com.github.health.check.service.NotificationService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class CreateNoticeJob extends QuartzJobBean {

    @Autowired
    FlipService flipService;

    @Autowired
    ChannelService channelService;

    @Autowired
    NotificationService notificationService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HashMap<String, List<Channel>> cacheChannelMap = new HashMap<>();
        List<Flip> flips = flipService.getUnhandleFlips();
        if (flips.size() == 0) {
            return;
        }
        for (Flip flip : flips) {
            String projectName = flip.getProjectName();
            String checkName = flip.getCheckName();
            String key = bulidKey(projectName, checkName);
            List<Channel> channelList = cacheChannelMap.get(key);
            if (channelList == null) {
                channelList = channelService.getChannel(projectName, checkName);
                if (channelList == null) {
                    // 记录日志

                    cacheChannelMap.put(key, new ArrayList<>());
                } else {
                    cacheChannelMap.put(key, channelList);
                }

            }
            handleOneFlip(flip, channelList);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleOneFlip(Flip flip, List<Channel> channelList) {
        List<Notification> notificationList = new ArrayList<>();
        for (Channel channel: channelList) {
            Notification notification = new Notification();
            notification.setMethod(channel.getKind());
            notification.setTo(channel.getValue());
            notification.setCreated(new Date());
            notification.setCheckName(flip.getCheckName());
            notification.setError(flip.getContent());
            notification.setStatus("N");
            notificationList.add(notification);
        }
        notificationService.saveBatch(notificationList);
        // 更新flip状态，表示已处理
        flip.setStatus("Y");
        flipService.saveOrUpdate(flip);
    }

    private String bulidKey(String projectName, String checkName) {
        return projectName+"-"+checkName;
    }
}
