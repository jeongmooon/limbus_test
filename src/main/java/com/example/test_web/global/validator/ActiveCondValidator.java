package com.example.test_web.global.validator;

import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.TypeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ActiveCondValidator implements ConstraintValidator<ActiveCondVaild, List<String>> {

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true; // null 또는 빈 리스트는 허용
        }

        // 모든 값이 허용 타입에 포함되는지 확인
        boolean isValid = TypeUtils.ACTIVE_COND.containsAll(values);

        if (!isValid) {
            // 기본 검증 메시지 비활성화
            context.disableDefaultConstraintViolation();

            // ErrorCode의 메시지로 동적 검증 메시지 추가
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_ACTIVE_COND.getCode())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
