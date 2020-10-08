package com.github.health.check.message;

import com.github.health.check.message.dingding.DingDingMsgSender;
import com.github.health.check.message.email.MailMsgSender;
import com.github.health.check.message.wechat.WechatMsgSender;
import com.github.health.check.util.SpringContextUtil;

public class MsgSenderFactory {

    private MsgSenderFactory(){

    }

    /**
     * 后续用枚举类型优化
     * @param type
     * @return
     */
    public static MsgSender getMsgSender(String type) {
        if ("email".equals(type)) {
            return SpringContextUtil.getBean(MailMsgSender.class);
        } else if ("dingding".equals(type)){
            return SpringContextUtil.getBean(DingDingMsgSender.class);
        } else if ("wachat".equals(type)) {
            return SpringContextUtil.getBean(WechatMsgSender.class);
        }
        return SpringContextUtil.getBean(MailMsgSender.class);
    }
}
