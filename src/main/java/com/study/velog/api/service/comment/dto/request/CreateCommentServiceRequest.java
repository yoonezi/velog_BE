package com.study.velog.api.service.comment.dto.request;

import lombok.Builder;

@Builder
public record CreateCommentServiceRequest(
        String content,
        Long postId
) {}
