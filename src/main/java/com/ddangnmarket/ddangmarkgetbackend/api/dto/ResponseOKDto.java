package com.ddangnmarket.ddangmarkgetbackend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseOKDto<T> {
    private String code;
    private int status;
    private String message;
    private String timestamp;
    private T data;

    public ResponseOKDto(T data){
        this.data = data;
        code = HttpStatus.OK.name();
        status = HttpStatus.OK.value();
        timestamp = LocalDateTime.now().toString();
        message = HttpStatus.OK.name();
    }
}
