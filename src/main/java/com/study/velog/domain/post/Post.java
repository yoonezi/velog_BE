package com.study.velog.domain.post;

import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.BaseTimeEntity;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.type.PostCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member", "postTags"})
public class Post extends BaseTimeEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<PostTag> postTags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PostCategory categoryType;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostImage> postImageList = new HashSet<>();

    private int viewCount;

    @Builder
    public Post(
            Long postId,
            Member member,
            String content,
            String title,
            Set<PostTag> postTags,
            PostCategory categoryType,
            PostStatus postStatus,
            Set<PostImage> postImageList
    ) {
        this.postId = postId;
        this.member = member;
        this.content = content;
        this.title = title;
        this.postTags = postTags;
        this.categoryType = categoryType;
        this.postStatus = postStatus;
        this.postImageList = postImageList;
    }

    public static Post create(
            Member member,
            String content,
            String title,
            PostCategory categoryType
    ) {
        return Post.builder()
                .member(member)
                .content(content)
                .title(title)
                .categoryType(categoryType)
                .postStatus(PostStatus.SERVICED)
                .build();
    }

    public void update(String title, String content, PostCategory categoryType, List<PostImage> postImages)
    {
        setTitle(title);
        setContent(content);
        setCategoryType(categoryType);
        setPostImage(postImages);
    }

    private void setTitle(String title)
    {
        if (title == null || title.isBlank())
        {
            return;
        }
        this.title = title;
    }

    private void setContent(String content)
    {
        if (content == null || content.isBlank())
        {
            return;
        }
        this.content = content;
    }

    public void addPostTag(PostTag postTag)
    {
        if (this.getPostTags() == null)
        {
            this.postTags = new HashSet<>();
        }

        this.postTags.add(postTag);
        postTag.setPost(this);
    }

    private void setCategoryType(PostCategory categoryType)
    {
        if (categoryType == null)
        {
            return;
        }

        this.categoryType = categoryType;
    }

    public void addPostImage(PostImage postImage)
    {
        if (this.postImageList == null)
        {
            this.postImageList = new HashSet<>();
        }

        this.getPostImageList().add(postImage);
        postImage.setPost(this);
    }

    private void setPostImage(List<PostImage> postImages)
    {
        this.getPostImageList().clear();
        postImages.forEach(postImage -> this.addPostImage(postImage));
    }

    public void delete()
    {
        if (postStatus.equals(PostStatus.DELETED))
        {
            throw new ApiException(ErrorCode.POST_STATUS_DELETED);
        }

        this.postStatus = PostStatus.DELETED;
    }

    public void setViewCount(int viewCount)
    {
        this.viewCount = viewCount;
    }
}
