package com.study.velog.api.service.post.dto.request;

import com.study.velog.api.service.postImage.dto.request.CreatePostImageServiceRequest;
import com.study.velog.domain.type.PostCategory;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatePostServiceRequest(
        String title,
        String content,
        List<String> tagList,
        PostCategory categoryType,
        List<CreatePostImageServiceRequest> postImageRequestList
) {}
