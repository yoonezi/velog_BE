package com.study.velog.event;

import lombok.Getter;

@Getter
public class CommentCreatedEvent {
    private Long postId;
    private Long commentId;

    private Long commentWriterId;
    private Long memberId;
}
