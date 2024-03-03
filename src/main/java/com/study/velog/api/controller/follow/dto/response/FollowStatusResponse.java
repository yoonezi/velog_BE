package com.study.velog.api.controller.follow.dto.response;

import com.study.velog.domain.follow.Follow;
import com.study.velog.domain.follow.FollowStatus;
import lombok.Builder;

@Builder
public record FollowStatusResponse(
        Long followerId,
        Long followeeId,
        FollowStatus followStatus
) {
    public static FollowStatusResponse of(Follow follow)
    {
        if (follow == null)
        {
            return new FollowStatusResponse(
                    null,
                    null,
                    FollowStatus.UNFOLLOW
            );
        }
        return new FollowStatusResponse(
                follow.getFollowerId(),
                follow.getFolloweeId(),
                FollowStatus.FOLLOW
        );
    }
}
