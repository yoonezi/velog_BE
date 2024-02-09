package com.study.velog.api.service.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostCreatedEvent {
    private Long postId;
    public static PostCreatedEvent of(Long postId) {
        return new PostCreatedEvent(postId);
    }
}
