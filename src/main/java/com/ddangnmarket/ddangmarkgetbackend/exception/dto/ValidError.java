package com.ddangnmarket.ddangmarkgetbackend.exception.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidError{
    private String field;
    private String message;
}