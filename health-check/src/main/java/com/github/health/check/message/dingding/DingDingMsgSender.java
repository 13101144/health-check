package com.github.health.check.message.dingding;

import com.github.health.check.domain.entity.Notification;
import com.github.health.check.message.MsgSender;
import org.springframework.stereotype.Component;

@Component
public class DingDingMsgSender implements MsgSender {

    @Override
    public void send(Notification notification) {

    }

    @Override
    public void sendStyleWithHtml(Notification notification) {

    }
}
