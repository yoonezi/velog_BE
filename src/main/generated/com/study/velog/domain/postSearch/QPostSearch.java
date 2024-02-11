package com.study.velog.domain.postSearch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostSearch is a Querydsl query type for PostSearch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostSearch extends EntityPathBase<PostSearch> {

    private static final long serialVersionUID = 1641658703L;

    public static final QPostSearch postSearch = new QPostSearch("postSearch");

    public final EnumPath<com.study.velog.domain.type.PostCategory> categoryType = createEnum("categoryType", com.study.velog.domain.type.PostCategory.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath mainImageUrl = createString("mainImageUrl");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath memberName = createString("memberName");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final EnumPath<com.study.velog.domain.post.PostStatus> postStatus = createEnum("postStatus", com.study.velog.domain.post.PostStatus.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QPostSearch(String variable) {
        super(PostSearch.class, forVariable(variable));
    }

    public QPostSearch(Path<? extends PostSearch> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostSearch(PathMetadata metadata) {
        super(PostSearch.class, metadata);
    }

}

