package com.example.test_web.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) throws Exception {
        String accept = request.getHeader("Accept");

        log.error("GlobalExceptionHandler: {}", request.getRequestURI(), e);

        // 브라우저에서 온 HTML 요청이면, 예외를 다시 던져서 DispatcherServlet이 /error 로 넘기게 한다
        if (accept != null && accept.contains("text/html")) {
            throw e; // ★ 핵심 ★
        }

        // API 요청인 경우에만 JSON 반환
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.GLOBAL_EXCEPTION);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponse> handleBizException(BizException e) {
        log.error("handleBizException", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.MISSING_PARAMETER);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND_API);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_SUPPORTED_METHOD);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DetailErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.REQUEST_VALUE_INVALID);
        List<DetailErrorResponse.DetailError> detailErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> DetailErrorResponse.DetailError.builder()
                        .field(error.getField())
                        .value(error.getRejectedValue())
                        .reason(buildReason(error))
                        .build())
                .toList();

        return new ResponseEntity<>(new DetailErrorResponse(errorResponse, detailErrors), errorResponse.getStatus());
    }

    /**
     * FieldError 가 BindingFailure 이면 type-mismatch 와 같은 에러
     * 아니면 validation 에러
     * (like a type mismatch); else, it is a validation failure
     *
     * @param fieldError the filedError
     */
    private String buildReason(FieldError fieldError) {
        return fieldError.isBindingFailure() ? "입력 값을 확인해 주세요." : fieldError.getDefaultMessage();
    }

    // ConstraintViolationException을 먼저 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Validation error: {}", e.getMessage(), e);

        // ConstraintViolation에서 메시지 템플릿으로 코드 추출
        String errorCodeString = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate) // 메시지 템플릿에서 코드 추출
                .findFirst() // ConstraintViolation이 여러 개인 경우 첫 번째 코드 선택
                .orElse("CM1"); // 기본값 (예: GLOBAL_EXCEPTION)

        // `fromCode` 메서드를 사용해 Enum 객체 가져오기
        ErrorCode errorCode = ErrorCode.fromCode(errorCodeString);

        // ErrorResponse 생성
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorCode.getCode())            // 에러 코드
                .message(errorCode.getMessage())      // 에러 메시지
                .status(errorCode.getStatus())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String extractCodeFromTemplate(String messageTemplate) {
        // 메시지 템플릿이 "{CODE}" 형식일 때 CODE만 추출
        if (messageTemplate.startsWith("{") && messageTemplate.endsWith("}")) {
            return messageTemplate.substring(1, messageTemplate.length() - 1); // 중괄호 제거 후 반환
        }
        return "UNKNOWN_CODE"; // 기본값
    }
}
