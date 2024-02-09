package com.study.velog.domain.postSearch;

import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.type.PostCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Builder
public class PostSearch {

    // post <-> postSearch

    @Id
    private Long postId;
    private String memberName;
    private Long memberId;
    private String title;
    private String content;
    private PostCategory categoryType;
    private PostStatus postStatus;
    private String mainImageUrl;
    private int viewCount;
    private int likeCount;

    public PostSearch(
            Long postId,
            String memberName,
            Long memberId,
            String title,
            String content,
            PostCategory categoryType,
            PostStatus postStatus,
            String mainImageUrl,
            int viewCount,
            int likeCount
    ) {
        this.postId = postId;
        this.memberName = memberName;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.categoryType = categoryType;
        this.postStatus = postStatus;
        this.mainImageUrl = mainImageUrl;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }
}
