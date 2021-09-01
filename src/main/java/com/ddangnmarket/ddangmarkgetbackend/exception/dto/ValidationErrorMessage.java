package com.ddangnmarket.ddangmarkgetbackend.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorMessage {

    private List<ValidError> validationErrors= new ArrayList<>();

    public void addError(ValidError error){
        validationErrors.add(error);
    }
}
