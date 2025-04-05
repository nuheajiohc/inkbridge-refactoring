package com.nhnacademy.inkbridge.backend.api.support.validation.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.nhnacademy.inkbridge.backend.api.support.validation.EnumValidator;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValid {

	String message() default "지원 가능한 값은 [{enumValues}] 입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends java.lang.Enum<?>> target();

	boolean ignoreCase() default false;
}
