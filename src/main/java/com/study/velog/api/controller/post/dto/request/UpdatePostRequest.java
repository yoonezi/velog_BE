package com.study.velog.api.controller.post.dto.request;

import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import com.study.velog.domain.type.PostCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdatePostRequest(
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        String content,
        List<String> tagList,
        @NotNull(message = "카테고리는 필수입니다.")
        PostCategory categoryType
) {
    public UpdatePostServiceRequest toServiceDto(Long postId,UpdatePostRequest request)
    {
        return UpdatePostServiceRequest.builder()
                .postId(postId)
                .title(request.title)
                .content(request.content)
                .tagList(request.tagList)
                .categoryType(request.categoryType)
                .build();
    }
}
