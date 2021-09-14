package com.ddangnmarket.ddangmarkgetbackend.api.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ResponseSimpleOKDto {
    private String code;
    private int status;
    private String message;
    private String timestamp;

    public ResponseSimpleOKDto(){
        code = HttpStatus.OK.name();
        status = HttpStatus.OK.value();
        timestamp = LocalDateTime.now().toString();
        message = HttpStatus.OK.name();
    }
    public ResponseSimpleOKDto(String message){
        code = HttpStatus.OK.name();
        status = HttpStatus.OK.value();
        timestamp = LocalDateTime.now().toString();
        this.message = message;
    }
}
