package com.github.health.check.task;

import com.github.health.check.domain.entity.Notification;
import com.github.health.check.message.MsgSenderFactory;
import com.github.health.check.service.NotificationService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SendNoticeJob extends QuartzJobBean {

    @Autowired
    private NotificationService notificationService;

    /**
     * 这里仅是简单实现发送消息,后续优化
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<Notification> list = notificationService.getUnHandleNotification();
        for (Notification notification: list) {
            sendOneNotice(notification);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendOneNotice(Notification notification) {
        MsgSenderFactory.getMsgSender(notification.getMethod()).send(notification);
        notification.setStatus("Y");
        notificationService.save(notification);
    }
}
