package com.study.velog.api.service.feed;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostFeed(
        Long postId,
        String memberEmail,
        Long memberId,
        FeedTaskType task,
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime localDateTime
) {
    public static PostFeed of(Long postId, String memberEmail, Long memberId, FeedTaskType task)
    {
        return new PostFeed(postId, memberEmail, memberId, task, LocalDateTime.now());
    }
}