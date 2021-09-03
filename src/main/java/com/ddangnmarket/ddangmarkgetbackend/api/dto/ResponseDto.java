package com.ddangnmarket.ddangmarkgetbackend.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ResponseDto<T> {
    private String code;
    private int status;
    private String message;
    private String timestamp;
    private T data;

    public ResponseDto(T data, String code, int status, String message){
        this.data = data;
        this.code = code;
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
    }
}
