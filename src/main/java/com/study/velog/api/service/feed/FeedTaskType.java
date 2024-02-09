package com.study.velog.api.service.feed;

import lombok.Getter;

@Getter
public enum FeedTaskType {
    ADD_COMMENT("댓글 추가"),
    POST_LIKE("포스트 좋아요"),
    ADD_FOLLOW("팔로우"),
    ;

    FeedTaskType(String content) {
        this.content = content;
    }

    private final String content;
}
