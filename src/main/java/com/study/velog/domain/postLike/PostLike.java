package com.study.velog.domain.postLike;

import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString(exclude = {"post", "member"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_post_like", unique = true, columnList = "post_id, member_id"))
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postLike_id")
    private Long postLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    @Builder
    public PostLike(
            Long postLikeId,
            Post post,
            Member member,
            LikeStatus likeStatus)
    {
        this.postLikeId = postLikeId;
        this.post = post;
        this.member = member;
        this.likeStatus = likeStatus;
    }

    public static PostLike create(
            Post post,
            Member member
    ) {
        return PostLike.builder()
                .post(post)
                .member(member)
                .likeStatus(LikeStatus.LIKE)
                .build();
    }

    public void delete()
    {
        if (likeStatus.equals(LikeStatus.UNLIKE))
        {
            throw new ApiException(ErrorCode.LIKE_STATUS_UNLIKED);
        }

        this.likeStatus = LikeStatus.UNLIKE;
    }
}
