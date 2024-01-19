package com.study.velog.api.service.post.dto.request;

import lombok.Builder;

@Builder
public record UpdatePostServiceRequest (
        Long postId,

        String title,

        String content
) {}
