package com.study.velog.api.controller.post.dto.response;

import com.study.velog.domain.post.Post;

import java.util.List;

public record PostResponse(
        Long postId,
        String memberName,
        Long memberId,
        String title,
        String content,
        List<String> postTags
) {
    public static PostResponse of(Post post)
    {
        return new PostResponse(
                post.getPostId(),
                post.getMember().getNickname(),
                post.getMember().getMemberId(),
                post.getTitle(),
                post.getContent(),
                post.getPostTags().stream()
                        .map(s->s.getTag().getTagContent())
                        .toList()
        );
    }
}
