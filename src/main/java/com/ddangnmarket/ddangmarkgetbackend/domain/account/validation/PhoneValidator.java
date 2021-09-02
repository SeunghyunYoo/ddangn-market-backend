package com.ddangnmarket.ddangmarkgetbackend.domain.account.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^\\d{3}-\\d{4}-\\d{4}$");
    }
}
