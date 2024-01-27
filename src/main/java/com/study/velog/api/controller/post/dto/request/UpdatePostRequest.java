package com.study.velog.api.controller.post.dto.request;

import com.study.velog.api.controller.postImage.dto.request.UpdatePostImageRequest;
import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import com.study.velog.api.service.postImage.dto.request.UpdatePostImageServiceRequest;
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
        PostCategory categoryType,

        List<UpdatePostImageRequest> postImageRequestList
) {
    public UpdatePostServiceRequest toServiceDto(Long postId)
    {
        List<UpdatePostImageServiceRequest> postImageList = postImageRequestList.stream()
                .map(UpdatePostImageRequest::of)
                .toList();

        return UpdatePostServiceRequest.builder()
                .postId(postId)
                .title(this.title)
                .content(this.content)
                .tagList(this.tagList)
                .categoryType(this.categoryType)
                .postImageRequestList(postImageList)
                .build();
    }
}
