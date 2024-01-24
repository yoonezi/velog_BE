package com.study.velog.api.controller.postLike.dto.request;

import com.study.velog.api.service.postLike.dto.request.CreatePostLikeServiceRequest;
import lombok.Builder;

@Builder
public record CreatePostLikeRequest (
        Long postId
) {
    public CreatePostLikeServiceRequest toServiceDto(Long postId)
    {
        return CreatePostLikeServiceRequest.builder()
                .postId(postId)
                .build();
    }
}
