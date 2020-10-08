package com.github.health.check.message.email;

import com.github.health.check.domain.entity.Notification;
import com.github.health.check.message.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailMsgSender implements MsgSender {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void send(Notification notification) {

        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(notification.getTo());
        //邮件主题
        message.setSubject(notification.getCheckName()+"任务异常提醒");
        //邮件内容
        message.setText(notification.getError());
        //发送邮件
        mailSender.send(message);

    }

    /**
     * 需要设计一个html模板
     * @param notification
     */
    @Override
    public void sendStyleWithHtml(Notification notification) {

    }
}
