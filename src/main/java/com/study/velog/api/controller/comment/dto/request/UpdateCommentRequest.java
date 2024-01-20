package com.study.velog.api.controller.comment.dto.request;

import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateCommentRequest (
        Long commentId,
        @NotBlank(message = "내용은 필수입니다.")
        String content,
        Long memberId,
        Long postId
) {
    public static UpdateCommentServiceRequest toServiceDto(UpdateCommentRequest request)
    {
        return UpdateCommentServiceRequest.builder()
                .commentId(request.commentId)
                .content(request.content)
                .postId(request.postId)
                .memberId(request.memberId)
                .build();
    }
}
