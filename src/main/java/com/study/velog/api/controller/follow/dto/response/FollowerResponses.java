package com.study.velog.api.controller.follow.dto.response;

import com.study.velog.domain.member.Member;
import lombok.Builder;

import java.util.List;

@Builder
public record FollowerResponses (
        List<FollowerResponse> followerResponses
) {

    public static FollowerResponses of(List<Member> followingMembers)
    {
        List<FollowerResponse> followerResponses = followingMembers.stream()
                .map(FollowerResponse::of)
                .toList();

        return FollowerResponses.builder()
                .followerResponses(followerResponses)
                .build();
    }

    private record FollowerResponse(
            Long memberId,
            String nickname,
            String email
    ) {
        private static FollowerResponse of(Member member)
        {
            return new FollowerResponse(
                    member.getMemberId(),
                    member.getNickname(),
                    member.getEmail()
            );
        }
    }
}
