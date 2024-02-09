package com.study.velog.api.controller.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.type.PostCategory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostResponse(
        Long postId,
        String memberName,
        Long memberId,
        String title,
        String content,
        PostCategory categoryType,
        List<String> tagList,
        PostStatus postStatus,
        List<PostImageList> postImageList,
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime registerDate,
        int viewCount
) {
    public static PostResponse of(Post post)
    {
        return new PostResponse(
                post.getPostId(),
                post.getMember().getNickname(),
                post.getMember().getMemberId(),
                post.getTitle(),
                post.getContent(),
                post.getCategoryType(),
                post.getPostTags().stream()
                        .map(tag -> tag.getTag().getTagContent())
                        .toList(),
                post.getPostStatus(),
                getPostImageList(post),
                post.getRegisterDate(),
                post.getViewCount()
        );
    }

    private static List<PostImageList> getPostImageList(Post post) {
        return post.getPostImageList().stream()
                .map(postImage -> PostImageList.builder()
                        .url(postImage.getUrl())
                        .order(postImage.getImageOrder())
                        .build())
                .collect(Collectors.toList());
    }

    @Builder
    private record PostImageList (
            String url,
            int order
    ) {}
}
