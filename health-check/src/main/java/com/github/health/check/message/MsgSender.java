package com.github.health.check.message;

import com.github.health.check.domain.entity.Notification;

public interface MsgSender {

    void send(Notification notification);

    void sendStyleWithHtml(Notification notification);
}
