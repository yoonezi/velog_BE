package com.study.velog.api.service.member.dto.response;

import com.study.velog.domain.member.Member;
import lombok.Builder;

@Builder
public record MemberServiceResponse(
        Long memberId,
        String email,
        String nickname
) {
    public static MemberServiceResponse of(Member member)
    {
        return MemberServiceResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
