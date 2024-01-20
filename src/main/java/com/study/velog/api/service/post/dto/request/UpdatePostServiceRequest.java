package com.study.velog.api.service.post.dto.request;

import com.study.velog.domain.type.PostCategory;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdatePostServiceRequest (
        Long postId,
        String title,
        String content,
        List<String> tagList,
        PostCategory categoryType
) {}
