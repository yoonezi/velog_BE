package com.study.velog.api.controller.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.type.PostCategory;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record MainPostsResponse(
        int page,
        int size,
        List<PostResponse> postResponses
) {

    public static MainPostsResponse of(List<Post> posts)
    {
        List<PostResponse> postResponses = posts.stream()
                .filter(post -> post.getPostStatus() == PostStatus.SERVICED)
                .map(post -> new PostResponse(
                        post.getPostId(),
                        CollectionUtils.isEmpty(post.getPostImageList()) ? ""
                                : post.getPostImageList().stream().findAny().get().getUrl(),
                        post.getMember().getMemberId(),
                        post.getMember().getNickname(),
                        post.getTitle(),
                        post.getRegisterDate(),
                        post.getContent(),
                        post.getViewCount(),
                        post.getCategoryType(),
                        post.getPostTags().stream()
                                .map(s->s.getTag().getTagContent())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return MainPostsResponse.builder()
                .page(0)
                .size(0)
                .postResponses(postResponses)
                .build();
    }

    public record PostResponse(
            Long postId,
            String mainImageUrl,
            Long memberId,
            String memberName,
            String title,
            @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime registerDate,
            String content,
            int viewCount,
            PostCategory postCategory,
            List<String> tags
    ) {}
}

