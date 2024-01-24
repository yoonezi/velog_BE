package com.study.velog.api.controller.comment.dto.request;

import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateCommentRequest (
        Long commentId,
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
    public UpdateCommentServiceRequest toServiceDto(Long commentId)
    {
        return UpdateCommentServiceRequest.builder()
                .commentId(commentId)
                .content(this.content)
                .build();
    }
}
