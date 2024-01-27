package com.study.velog.api.controller.post.dto.request;

import com.study.velog.api.controller.postImage.dto.request.CreatePostImageRequest;
import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.api.service.postImage.dto.request.CreatePostImageServiceRequest;
import com.study.velog.domain.type.PostCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatePostRequest (
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        String content,
        List<String> tagList,
        @NotNull(message = "카테고리는 필수입니다.")
        PostCategory categoryType,
        List<CreatePostImageRequest> postImageRequestList

) {
    public CreatePostServiceRequest toServiceDto()
    {
        List<CreatePostImageServiceRequest> postImageList = postImageRequestList.stream()
                .map(CreatePostImageRequest::of)
                .toList();

        return CreatePostServiceRequest.builder()
                .title(this.title)
                .content(this.content)
                .tagList(this.tagList)
                .categoryType(this.categoryType)
                .postImageRequestList(postImageList)
                .build();
    }
}
