package com.ddangnmarket.ddangmarkgetbackend.domain.category.validation;

import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CategoryValidator implements ConstraintValidator<Category, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(CategoryTag.values()).map(CategoryTag::name).anyMatch(value::equalsIgnoreCase);
    }
}
