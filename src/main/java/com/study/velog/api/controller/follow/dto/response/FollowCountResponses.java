package com.study.velog.api.controller.follow.dto.response;

public record FollowCountResponses (
        Long memberId,
        String memberEmail,
        int followerCount,
        int followingCount
) {

    public static FollowCountResponses of(Long memberId, String memberEmail, int followerId, int followingId)
    {
        return new FollowCountResponses(
                memberId,
                memberEmail,
                followerId,
                followingId
        );
    }
}
