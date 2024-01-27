package com.study.velog.api.service.postImage.dto.request;

import lombok.Builder;

@Builder
public record CreatePostImageServiceRequest(
        String url,
        int order
) {}
