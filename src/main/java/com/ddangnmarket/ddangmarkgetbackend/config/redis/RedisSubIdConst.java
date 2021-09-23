package com.ddangnmarket.ddangmarkgetbackend.config.redis;

import java.util.UUID;

/**
 * @author SeunghyunYoo
 */
public interface RedisSubIdConst {
    String NOT_SUB_MSG = UUID.randomUUID().toString();
    String CHAT_ROOM = "sub-chatroom";
    String NOTIFICATION_MSG = "sub-notification-msg";
}
