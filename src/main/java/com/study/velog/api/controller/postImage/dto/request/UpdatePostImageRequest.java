package com.study.velog.api.controller.postImage.dto.request;

import com.study.velog.api.service.postImage.dto.request.UpdatePostImageServiceRequest;
import jakarta.validation.constraints.NotBlank;

public record UpdatePostImageRequest (
        @NotBlank(message = "포스트 이미지 url은 필수입니다.")
        String url,

        int order
){
    public UpdatePostImageServiceRequest of()
    {
        return new UpdatePostImageServiceRequest(this.url, this.order);
    }
}