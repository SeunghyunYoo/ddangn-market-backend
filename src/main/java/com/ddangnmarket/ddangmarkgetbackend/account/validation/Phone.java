package com.ddangnmarket.ddangmarkgetbackend.account.validation;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { PhoneValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Phone {
    String message() default "xxx-xxxx-xxxx 형식이여야 합니다";
    Class[] groups() default {};
    Class[] payload() default {};
}
