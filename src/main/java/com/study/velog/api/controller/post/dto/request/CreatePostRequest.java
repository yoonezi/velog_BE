package com.study.velog.api.controller.post.dto.request;

import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.domain.type.PostCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatePostRequest (
        Long memberId,
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        String content,
        List<String> tagList,
        @NotNull(message = "카테고리는 필수입니다.")
        PostCategory categoryType
        // Tag Post ==>
        // Tag (1, 'tag1')
        // Tag (2, 'tag2')
        // Tag (3, 'tag3')
        // Tag (4, 'tag4')

        // PostTag Table (1, 1),
        // PostTag Table (1, 2),
        // PostTag Table (1, 3),
        // PostTag Table (2, 1),
        // PostTag Table (2, 2),
        // PostTag Table (2, 4),


        // Tag (yz1, yz2)


        // Post , PostTag
        // PostTag Table (1, 'tag1'),
        // PostTag Table (1, 'tag2'),
        // PostTag Table (1, 'tag3'),
        // PostTag Table (2, 'tag1'),
        // PostTag Table (2, 'tag2'),
        // PostTag Table (2, 'tag4'),
) {
    public CreatePostServiceRequest toServiceDto()
    {
        return CreatePostServiceRequest.builder()
                .memberId(this.memberId)
                .title(this.title)
                .content(this.content)
                .tagList(this.tagList)
                .categoryType(this.categoryType)
                .build();
    }
}
