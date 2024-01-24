package com.study.velog.api.controller.comment.dto.response;

import com.study.velog.domain.comment.Comment;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchCommentResponses (
        int page,
        int size,
        List<SearchCommentResponse> searchCommentResponses
) {
    public static SearchCommentResponses of(List<Comment> content, int page, int size)
    {
        List<SearchCommentResponse> commentResponses = content.stream()
                .map(SearchCommentResponse::of)
                .toList();

        return SearchCommentResponses.builder()
                .page(page)
                .size(size)
                .searchCommentResponses(commentResponses)
                .build();
    }

    private record SearchCommentResponse (
            Long commentId,
            Long postId,
            Long memberId,
            String content

    ) {
        private static SearchCommentResponse of(Comment comment)
        {
            return new SearchCommentResponse(
                    comment.getCommentId(),
                    comment.getPost().getPostId(),
                    comment.getMember().getMemberId(),
                    comment.getContent()
            );
        }
    }
}
