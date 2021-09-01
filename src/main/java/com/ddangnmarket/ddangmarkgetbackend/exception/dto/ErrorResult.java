package com.ddangnmarket.ddangmarkgetbackend.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private int status;
    private String message;
    private String timestamp;
//    private String path;
//    private String error;

    public ErrorResult(String code, int status, String message){
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
    }
}
