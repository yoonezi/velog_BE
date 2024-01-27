package com.study.velog.api.controller.postImage.dto.request;

import com.study.velog.api.service.postImage.dto.request.CreatePostImageServiceRequest;
import jakarta.validation.constraints.NotBlank;

public record CreatePostImageRequest (
        @NotBlank(message = "포스트 이미지 url은 필수입니다.")
        String url,
        int order
){
    public CreatePostImageServiceRequest of()
    {
        return new CreatePostImageServiceRequest(this.url, this.order);
    }
}
