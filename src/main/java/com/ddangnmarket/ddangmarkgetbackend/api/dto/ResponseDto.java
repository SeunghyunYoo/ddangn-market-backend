package com.ddangnmarket.ddangmarkgetbackend.api.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class ResponseDto<T> {
    private String code;
    private int status;
    private String message;
    private String timestamp;
    private T data;

    public ResponseDto(T data,HttpStatus httpStatus, String message){
        this.data = data;
        this.code = httpStatus.name();
        this.status = httpStatus.value();
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
    }
}
