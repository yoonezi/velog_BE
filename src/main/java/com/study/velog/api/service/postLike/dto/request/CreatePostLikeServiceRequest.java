package com.study.velog.api.service.postLike.dto.request;

import lombok.Builder;

@Builder
public record CreatePostLikeServiceRequest(
        Long postId
) {
}
