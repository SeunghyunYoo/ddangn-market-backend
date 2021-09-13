package com.ddangnmarket.ddangmarkgetbackend.domain.category.validation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { CategoryValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Category {
    String message() default "존재하지 않는 카테고리입니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
