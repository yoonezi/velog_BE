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
public record MyPendingPostsResponse(
        int page,
        int size,
        long totalElementCount,
        List<PostResponse> postResponses
){

    public static MyPendingPostsResponse of(Page<PostResponses>response)
{
    List<PostResponse> pendingPostResponses = response.stream()
            .filter(res -> res.getPostStatus() == PostStatus.PENDING)
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

        return MyPendingPostsResponse.builder()
                .page(0)
                .size(0)
                .totalElementCount(response.getTotalElements())
        .postResponses(pendingPostResponses)
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
}
