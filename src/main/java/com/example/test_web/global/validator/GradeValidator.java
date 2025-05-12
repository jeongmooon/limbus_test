package com.example.test_web.global.validator;

import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.TypeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class GradeValidator implements ConstraintValidator<GradeVaild, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_GRADE.getCode())
                    .addConstraintViolation();
            return false;
        }

        // 모든 값이 허용 타입에 포함되는지 확인
        boolean isValid = TypeUtils.ALLOWED_GRADE.contains(value);

        if (!isValid) {
            // 기본 검증 메시지 비활성화
            context.disableDefaultConstraintViolation();

            // ErrorCode의 메시지로 동적 검증 메시지 추가
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_GRADE.getCode())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
