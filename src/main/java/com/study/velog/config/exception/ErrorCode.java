package com.study.velog.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    //Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 없습니다."),
    MEMBER_EMAIL_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일이 중복되었습니다."),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 없습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage)
    {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
