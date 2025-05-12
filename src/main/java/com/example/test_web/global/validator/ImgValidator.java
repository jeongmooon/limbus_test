package com.example.test_web.global.validator;

import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.TypeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImgValidator implements ConstraintValidator<ImgValid, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // 빈 파일 허용하지 않음
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_IMG_CONTENT_TYPE.getCode())
                    .addConstraintViolation();
            return false;
        }

        boolean isValid = TypeUtils.ALLOWED_IMG_CONTENT_TYPE.contains(file.getContentType());

        if (!isValid) {
            // 기본 검증 메시지 비활성화
            context.disableDefaultConstraintViolation();

            // ErrorCode의 메시지로 동적 검증 메시지 추가
            context.buildConstraintViolationWithTemplate(ErrorCode.NOT_VALID_IMG_CONTENT_TYPE.getCode())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
