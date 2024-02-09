package com.study.velog.api.service.feed;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FollowFeed (
        String memberEmail,
        FeedTaskType task,
        Long memberId,
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime localDateTime
) {
    public static FollowFeed of(String memberEmail, Long memberId, FeedTaskType task)
    {
        return new FollowFeed(memberEmail, task, memberId, LocalDateTime.now());
    }

}