package com.gamjiduck.nlp.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, 200, "요청에 성공하였습니다."),

    /**
     * 300 : Request 오류
     */
    DUPLICATED_USERNAME(false, 301, "중복된 아이디입니다."),
    DUPLICATED_NICKNAME(false, 302, "중복된 닉네임입니다."),
    NOT_FOUND_USERNAME(false, 303, "없는 아이디입니다."),
    INVALID_PASSWORD(false, 304, "잘못된 비밀번호입니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
