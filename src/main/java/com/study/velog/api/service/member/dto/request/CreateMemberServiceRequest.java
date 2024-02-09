package com.study.velog.api.service.member.dto.request;

import com.study.velog.domain.member.MemberRole;
import lombok.Builder;

@Builder
public record CreateMemberServiceRequest (
        String email,
        String nickname,
        String password,
        String confirmPassword
) {}
