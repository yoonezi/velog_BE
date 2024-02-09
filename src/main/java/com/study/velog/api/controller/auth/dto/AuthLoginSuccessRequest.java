package com.study.velog.api.controller.auth.dto;


public record AuthLoginSuccessRequest(
        String email,
        String password
) {}
