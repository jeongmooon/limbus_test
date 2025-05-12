package com.example.test_web.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImgValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImgValid {
    String message() default "올바르지 않은 이미지 파일입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
