package com.study.velog.api.service.upload;

import lombok.Builder;

@Builder
public record PostImageUploadResponse(
        String originFileName,
        String url
) {}
