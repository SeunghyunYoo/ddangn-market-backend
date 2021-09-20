package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author SeunghyunYoo
 */
public enum MessageType {
    ENTER, READ, TALK, LEAVE;

    @JsonCreator
    public static MessageType fromString(String key){
        for(MessageType messageType : MessageType.values()){
            if(messageType.name().equalsIgnoreCase(key)){
                return messageType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 message type");
    }
}
