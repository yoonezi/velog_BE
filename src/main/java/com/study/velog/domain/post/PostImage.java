package com.study.velog.domain.post;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post"})
public class PostImage {
    @Id
    @Column(name = "post_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;

    @Comment("이미지 url")
    @Column(name = "post_url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "post_image_order", nullable = false)
    private int imageOrder;

    @Builder
    public PostImage(Long postImageId, String url, Post post, int imageOrder) {
        this.postImageId = postImageId;
        this.url = url;
        this.post = post;
        this.imageOrder = imageOrder;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }
}
