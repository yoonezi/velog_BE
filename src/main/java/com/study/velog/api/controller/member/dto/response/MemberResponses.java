package com.study.velog.api.controller.member.dto.response;

import com.study.velog.api.service.member.dto.response.MemberServiceResponse;
import com.study.velog.api.service.member.dto.response.MemberServiceResponses;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberResponses (
        List<MemberResponse> memberResponses
) {
    public static List<MemberResponse> of(MemberServiceResponses members)
    {
        return members.memberServiceResponses()
                .stream()
                .map(s -> new MemberResponse(s.memberId(), s.email(), s.nickname()))
                .toList();
    }

    public static MemberResponses toList(List<MemberResponse> members)
    {
        return new MemberResponses(members);
    }

    @Builder
    public record MemberResponse(
            Long memberId,
            String email,
            String nickname
    ) {
        public static MemberResponse of(MemberServiceResponse response)
        {
            return MemberResponse.builder()
                    .memberId(response.memberId())
                    .email(response.email())
                    .nickname(response.nickname())
                    .build();
        }
    }
}
