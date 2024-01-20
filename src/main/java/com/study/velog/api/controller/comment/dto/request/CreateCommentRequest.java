package com.study.velog.api.controller.comment.dto.request;

import com.study.velog.api.service.comment.dto.request.CreateCommentServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCommentRequest (
        @NotBlank(message = "내용은 필수입니다.")
        String content,
        Long memberId,
        Long postId
) {
    public static CreateCommentServiceRequest toServiceDto(CreateCommentRequest request)
    {
        return CreateCommentServiceRequest.builder()
                .content(request.content)
                .memberId(request.memberId)
                .postId(request.postId)
                .build();
    }
}
