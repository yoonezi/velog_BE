package com.study.velog.config.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode)
    {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
