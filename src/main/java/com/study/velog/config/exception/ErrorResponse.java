package com.study.velog.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(
        String message,
        String errorCode,
        int status
) {
    public static ErrorResponse badRequest(String message)
    {
        return new ErrorResponse(
                message,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            HttpStatus.BAD_REQUEST.value()
        );
    }

    public static ErrorResponse error(String message)
    {
        return new ErrorResponse(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    public static ResponseEntity<ErrorResponse> apiError(ErrorCode errorCode)
    {
        return
                ResponseEntity.status(errorCode.getHttpStatus())
                        .body(
                                new ErrorResponse(
                                        errorCode.getErrorMessage(),
                                        errorCode.getHttpStatus().getReasonPhrase(),
                                        errorCode.getHttpStatus().value()
                                )
                        );
    }
}
