package com.study.velog.api.service.dto;

import lombok.Builder;

@Builder
public record CreateMemberServiceRequest (
        String email,
        String nickname
) {}
