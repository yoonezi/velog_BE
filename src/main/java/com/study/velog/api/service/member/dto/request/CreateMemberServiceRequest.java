package com.study.velog.api.service.member.dto.request;

import lombok.Builder;

@Builder
public record CreateMemberServiceRequest (
        String email,
        String nickname
) {}
