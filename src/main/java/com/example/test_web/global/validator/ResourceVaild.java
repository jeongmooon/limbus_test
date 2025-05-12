package com.example.test_web.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ResourceValidator.class) // 새로 작성할 Validator 연결
@Target({ ElementType.PARAMETER }) // 메서드 매개변수에 적용
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceVaild {
    String message() default "유효하지 않는 값이 포함되어 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
