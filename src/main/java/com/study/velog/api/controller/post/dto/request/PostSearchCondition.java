package com.study.velog.api.controller.post.dto.request;

import com.study.velog.domain.type.PostCategory;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PostSearchCondition {
    private PostCategory postCategory;
    private Pageable pageable;
    private PostSortType postSortType;
    private Long memberId;

    public PostSearchCondition(PostCategory postCategory, Pageable pageable, PostSortType postSortType, Long memberId)
    {
        this.postCategory = postCategory;
        this.pageable = pageable;
        this.postSortType = postSortType;
        this.memberId = memberId;
    }
}
