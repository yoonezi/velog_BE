package com.study.velog.api.controller.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.velog.domain.member.Member;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberResponse(
        Long memberId,
        String email,
        String nickname,
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime registerDate
) {
    public static MemberResponse of(Member member)
    {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .registerDate(member.getRegisterDate())
                .build();
    }
}