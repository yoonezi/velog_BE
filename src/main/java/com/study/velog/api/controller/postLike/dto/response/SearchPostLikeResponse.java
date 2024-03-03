package com.study.velog.api.controller.postLike.dto.response;

import com.study.velog.domain.postLike.LikeStatus;
import com.study.velog.domain.postLike.PostLike;
import lombok.Builder;

@Builder
public record SearchPostLikeResponse (
        Long postId,
        Long memberId,
        LikeStatus likeStatus
) {
    public static SearchPostLikeResponse of(PostLike postLike)
    {
        return new SearchPostLikeResponse(
                postLike.getPostLikeId(),
                postLike.getPost().getPostId(),
                postLike.getLikeStatus()
        );
    }

    public static SearchPostLikeResponse empty()
    {
        return new SearchPostLikeResponse(
                null,
                null,
                LikeStatus.UNLIKE
        );
    }
}
