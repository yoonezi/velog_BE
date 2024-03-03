package com.study.velog.api.controller.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.velog.domain.post.PostStatus;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder
public record MemberPostsResponse(
        int page,
        int size,
        long totalElementCount,
        List<PostResponse> postResponses
) {
//    public static MyPostResponse of(int page, long totalElementCount, Page<Post> posts)
//    {
//        List<PostResponse> postResponses = posts.stream()
//                .filter(post -> post.getPostStatus() == PostStatus.SERVICED)
//                .map(post -> new PostResponse(
//                        post.getPostId(),
//                        CollectionUtils.isEmpty(post.getPostImageList()) ? "" : post.getPostImageList().stream().findAny().get().getUrl(),
//                        post.getTitle(),
//                        post.getMember().getNickname(),
//                        post.getRegisterDate(),
//                        post.getContent(),
//                        post.getViewCount()
//                ))
//                .collect(Collectors.toList());
//
//        return MyPostResponse.builder()
//                .page(page)
//                .size(posts.getSize())
//                .totalElementCount(totalElementCount)
//                .postResponses(postResponses)
//                .build();
//    }

    public static MemberPostsResponse of(Page<PostResponses> response)
    {
        List<PostResponse> myPostResponses = response.stream()
                .filter(res -> res.getPostStatus() == PostStatus.SERVICED)
                .map(res -> new PostResponse(
                        res.getPostId(),
                        CollectionUtils.isEmpty(res.getPostImageResponses()) ? ""
                                : res.getPostImageResponses().stream().findAny().get().getImageUrl(),
                        res.getTitle(),
                        res.getMemberName(),
                        res.getRegisterDate(),
                        res.getContent(),
                        res.getViewCount(),
                        res.getPostStatus()
                ))
                .collect(Collectors.toList());

        return MemberPostsResponse.builder()
                .page(0)
                .size(0)
                .totalElementCount(response.getTotalElements())
                .postResponses(myPostResponses)
                .build();
    }

    private record PostResponse(
            Long postId,
            String mainImageUrl,
            String title,
            String memberName,
            @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime registerDate,
            String content,
            int viewCount,
            PostStatus postStatus
    ) {}
//    public static PostResponse of(Long postId, String mainImageUrl, String title, String memberName, LocalDateTime registerDate, String content, int viewCount) {
//        return new PostResponse(postId, mainImageUrl, title, memberName, registerDate, content, viewCount);
//    }
}

