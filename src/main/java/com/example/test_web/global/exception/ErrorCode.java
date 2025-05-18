package com.example.test_web.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // USER_INFO
    EXISTS_USER_ID("UI1", 400, "이미 존재하는 아이디입니다."),
    EXCEPTION_USER_REGIST("UI2", 500, "아이디 생성 중 에러가 발생했습니다."),
    EXCEPTION_USER_LOGIN("UI3", 400, "로그인 정보가 올바르지 않습니다."),

    // Identitiy
    NO_IDENTITY_FOUND("ID1", 404, "해당 ID를 가진 인격이 없습니다."),

    // EGO
    NO_EGO_FOUND("ID1", 404, "해당 ID를 가진 에고가 없습니다."),

    //DICTION
    NOT_SINNER_DATA("DISK1",400,"해당 인격을 찾을 수 없습니다."),
    NOT_SEASON_DATA("DISK2",400,"해당 시즌을 찾을 수 없습니다."),
    NOT_GRADE_DATA("DISK3",400,"해당 등급을 찾을 수 없습니다."),
    NOT_KEYWORD_DATA("DISK4",400,"해당 키워드를 찾을 수 없습니다."),
    NOT_RESOURCE_DATA("DISK5",400,"해당 자원을 찾을 수 없습니다."),
    NOT_SKILL_TYPE_DATA("DISK6",400,"해당 스킬 타입을 찾을 수 없습니다."),

    //DECK
    NO_DECK_LIST_DATA("DCK1",404,"해당 유저의 덱 리스트를 찾을 수 없습니다."),
    DUPLICATED_DECK_SINNER("DCK2",400,"덱에는 중복되는 수감자는 들어갈 수 없습니다."),
    DUPLICATED_DECK_IDENTITY("DCK3",400,"덱에는 중복되는 인격은 들어갈 수 없습니다."),

    //BOARD
    INVALID_BOARD_CONTENT("BD1",400,"내용에 올바르지 않은 값이 입력되었습니다."),

    // COMMON
    GLOBAL_EXCEPTION("CM1", 500, "서버에서 에러가 발생하였습니다."),
    MISSING_PARAMETER("CM2", 400, "필수 파라미터가 존재하지 않습니다."),
    NOT_FOUND_API("CM3", 404, "요청하신 API를 찾을 수 없습니다."),
    NOT_SUPPORTED_METHOD("CM4", 400, "지원하지 않는 HTTP METHOD 입니다."),
    REQUEST_VALUE_INVALID("CMM5", 400, "입력값이 올바르지 않습니다."),
    NOT_IMAGE_FILE("CMM6", 400, "이미지가 없습니다."),

    //VALID
    NOT_VALID_SINNER("VAID1",400, "입력된 인격 값이 올바르지 않습니다."),
    NOT_VALID_RESOURCE("VAID2",400, "입력된 자원 값이 올바르지 않습니다."),
    NOT_VALID_GRADE("VAID3",400, "입력된 등급 값이 올바르지 않습니다."),
    NOT_VALID_SEASON("VAID4",400, "입력된 시즌 값이 올바르지 않습니다."),
    NOT_VALID_ACTIVE_COND("VAID5",400, "입력된 활성화 조건 값이 올바르지 않습니다."),
    NOT_VALID_IMG_CONTENT_TYPE("VAID6",400, "이미지 확장자가 올바르지 않습니다.");

    private final String code;
    private final int status;
    private final String message;

    /**
     * 에러 코드(code)로 Enum 객체 검색
     * @param code code명
     */
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        // 잘못된 코드는 기본값 (GLOBAL_EXCEPTION) 사용
        return GLOBAL_EXCEPTION;
    }
}
