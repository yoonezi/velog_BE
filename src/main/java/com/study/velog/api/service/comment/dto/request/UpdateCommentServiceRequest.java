package com.study.velog.api.service.comment.dto.request;

import lombok.Builder;

@Builder
public record UpdateCommentServiceRequest (
        Long commentId,
        String content,
        Long postId
) {}
