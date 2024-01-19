package com.study.velog.api.service.post.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatePostServiceRequest(
        Long memberId,
        String title,
        String content,
        List<String> tagList
) {}
