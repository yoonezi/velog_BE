package com.study.velog.api.service.postImage.dto.request;

import lombok.Builder;

@Builder
public record UpdatePostImageServiceRequest(
        String url,
        int order
) {}