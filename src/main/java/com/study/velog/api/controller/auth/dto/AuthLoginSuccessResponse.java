package com.study.velog.api.controller.auth.dto;

public record AuthLoginSuccessResponse(
        String accessToken,
        String email,
        String nickname,
        Long memberId
) {}
