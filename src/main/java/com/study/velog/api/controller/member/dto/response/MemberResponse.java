package com.study.velog.api.controller.member.dto.response;

import com.study.velog.domain.member.Member;
import lombok.Builder;

@Builder
public record MemberResponse(
        Long memberId,
        String email,
        String nickname
) {
    public static MemberResponse of(Member member)
    {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}