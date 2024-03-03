package com.study.velog.api.controller.follow.dto.response;

import com.study.velog.domain.member.Member;
import lombok.Builder;

import java.util.List;

@Builder
public record FollowingResponses(
        List<FollowingResponse> followingResponses
) {

    public static FollowingResponses of(List<Member> followingMembers)
    {
        List<FollowingResponse> followingResponses = followingMembers.stream()
                .map(FollowingResponse::of)
                .toList();

        return FollowingResponses.builder()
                .followingResponses(followingResponses)
                .build();
    }

    private record FollowingResponse(
            Long memberId,
            String nickname,
            String email
    ) {
        private static FollowingResponse of(Member member)
        {
            return new FollowingResponse(
                    member.getMemberId(),
                    member.getNickname(),
                    member.getEmail()
            );
        }
    }
}



