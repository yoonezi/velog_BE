package com.study.velog.api.controller.post.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainPostLikeCountOrder {
        private Long postId;
        private long likeCount;

    public MainPostLikeCountOrder(
            Long postId,
            long likeCount
    ) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
