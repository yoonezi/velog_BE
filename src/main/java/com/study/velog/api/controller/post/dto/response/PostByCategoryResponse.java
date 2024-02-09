package com.study.velog.api.controller.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.type.PostCategory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
public record PostByCategoryResponse(
        List<PostResponse> postResponses
) {
    public static PostByCategoryResponse of(Map<PostCategory, List<Post>> postsByCategory)
    {
        ///

//        return PostByCategoryResponse.builder()
//                .postResponses(postResponses)
//                .build();

        return null;
    }

    private record PostResponse(
            Long postId,
            String mainImageUrl,
            String title,
            String memberName,
            @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime registerDate,
            String content
    ) {
        public static PostResponse of(Post post) {
            return new PostResponse(
                    post.getPostId(),
                    post.getPostImageList().isEmpty() ? "" : post.getPostImageList().stream().findAny().get().getUrl(),
                    post.getTitle(),
                    post.getMember().getNickname(),
                    post.getRegisterDate(),
                    post.getContent()
            );
        }
    }
}
