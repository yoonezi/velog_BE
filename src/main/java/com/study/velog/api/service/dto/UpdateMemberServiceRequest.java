package com.study.velog.api.service.dto;

import lombok.Builder;

@Builder
public record UpdateMemberServiceRequest(
        Long memberId,
        String email,
        String nickname
) {}
