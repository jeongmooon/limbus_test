package com.example.test_web.global.validator;

import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.TypeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SeasonValidator implements ConstraintValidator<SeasonVaild, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_SEASON.getCode())
                    .addConstraintViolation();
            return false;
        }

        // 모든 값이 허용 타입에 포함되는지 확인
        boolean isValid = TypeUtils.ALLOWED_SEASON.contains(value);

        if (!isValid) {
            // 기본 검증 메시지 비활성화
            context.disableDefaultConstraintViolation();

            // ErrorCode의 메시지로 동적 검증 메시지 추가
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_SEASON.getCode())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
