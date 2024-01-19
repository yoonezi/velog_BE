package com.study.velog.api.service.member.dto.response;

import com.study.velog.domain.member.Member;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public record MemberServiceResponses (
        List<MemberServiceResponse> memberServiceResponses
){
    public static List<MemberServiceResponse> of(List<Member> members)
    {
        return members.stream()
                .map(MemberServiceResponse::of)
                .collect(Collectors.toList());
    }

    public static MemberServiceResponses toList(List<MemberServiceResponse> memberServiceResponses)
    {
        return new MemberServiceResponses(memberServiceResponses);
    }

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
}
