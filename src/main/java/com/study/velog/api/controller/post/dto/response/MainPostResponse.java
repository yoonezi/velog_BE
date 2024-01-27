package com.study.velog.api.controller.post.dto.response;

import com.study.velog.domain.post.Post;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record MainPostResponse(
        int page,
        int size,
        List<PostResponse> postResponses
) {
    public static MainPostResponse of(int page, Page<Post> posts)
    {
        List<PostResponse> postResponses = posts.stream()
                .map(post -> new PostResponse(
                        post.getPostImageList().get(0).getUrl(),
                        post.getTitle(),
                        post.getMember().getNickname(),
                        post.getRegisterDate()
                ))
                .collect(Collectors.toList());

        return MainPostResponse.builder().page(page).size(posts.getSize()).postResponses(postResponses).build();
    }

    private record PostResponse(
        String mainImageUrl,
        String title,
        String memberName,
        LocalDateTime regDate
    ){}
}
