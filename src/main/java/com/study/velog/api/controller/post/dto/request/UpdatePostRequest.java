package com.study.velog.api.controller.post.dto.request;

import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import lombok.Builder;

@Builder
public record UpdatePostRequest(
        String title,
        String content
) {
    public UpdatePostServiceRequest toServiceDto(Long postId,UpdatePostRequest request)
    {
        return UpdatePostServiceRequest.builder()
                .postId(postId)
                .title(request.title)
                .content(request.content)
                .build();
    }
}
