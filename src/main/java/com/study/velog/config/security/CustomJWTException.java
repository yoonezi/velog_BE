package com.study.velog.config.security;

public class CustomJWTException extends RuntimeException {

    public CustomJWTException(String message)
    {
        super(message);
    }
}
