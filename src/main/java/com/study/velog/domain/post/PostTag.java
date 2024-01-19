package com.study.velog.domain.post;

import com.study.velog.domain.BaseTimeEntity;
import com.study.velog.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString(exclude = {"post", "tag"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_post_tag", unique = true, columnList = "post_id, tag_id"))
public class PostTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void setPost(Post post)
    {
        this.post = post;
    }

    @Builder
    public PostTag(Long postTagId, Post post, Tag tag)
    {
        this.postTagId = postTagId;
        this.post = post;
        this.tag = tag;
    }
}
