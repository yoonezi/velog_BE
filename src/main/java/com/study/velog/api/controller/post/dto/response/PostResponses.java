package com.study.velog.api.controller.post.dto.response;

import com.study.velog.domain.type.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponses {
    private Long postId;
    private String title;
    private String content;
    private int viewCount;
    private PostCategory postCategory;
    private LocalDateTime registerDate;
    private Long memberId;
    private String memberName;
    private List<PostImageResponse> postImageResponses;
}