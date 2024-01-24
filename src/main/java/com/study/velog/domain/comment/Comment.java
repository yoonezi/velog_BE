package com.study.velog.domain.comment;

import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.BaseTimeEntity;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post", "member"})
public class Comment extends BaseTimeEntity {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    @Builder
    public Comment(
            Long commentId,
            String content,
            Post post,
            Member member,
            CommentStatus commentStatus
    ) {
        this.commentId = commentId;
        this.content = content;
        this.post = post;
        this.member = member;
        this.commentStatus = commentStatus;
    }

    public static Comment create(
            String content,
            Post post,
            Member member
    ) {
        return Comment.builder()
                .content(content)
                .member(member)
                .post(post)
                .commentStatus(CommentStatus.SERVICED)
                .build();
    }

    public void update(String content) {
        this.content = content;
    }

    public void delete()
    {
        if (commentStatus.equals(CommentStatus.DELETED))
        {
            throw new ApiException(ErrorCode.COMMENT_STATUS_DELETED);
        }

        this.commentStatus = CommentStatus.DELETED;
    }

}
