package com.ddangnmarket.ddangmarkgetbackend.config.redis;

import java.util.UUID;

/**
 * @author SeunghyunYoo
 */
public enum SubscriptionIdConst {
    CHAT_ROOM("sub-chatroom"),
    NOTIFICATION_MSG("sub-notification-msg");

    private final String subId;

    SubscriptionIdConst(String subId){
        this.subId = subId;
    }

    public String getSubId(){
        return this.subId;
    }
}
