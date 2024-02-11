package com.study.velog.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 326261327L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.study.velog.domain.QBaseTimeEntity _super = new com.study.velog.domain.QBaseTimeEntity(this);

    public final EnumPath<com.study.velog.domain.type.PostCategory> categoryType = createEnum("categoryType", com.study.velog.domain.type.PostCategory.class);

    public final StringPath content = createString("content");

    public final com.study.velog.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final SetPath<PostImage, QPostImage> postImageList = this.<PostImage, QPostImage>createSet("postImageList", PostImage.class, QPostImage.class, PathInits.DIRECT2);

    public final EnumPath<PostStatus> postStatus = createEnum("postStatus", PostStatus.class);

    public final SetPath<PostTag, QPostTag> postTags = this.<PostTag, QPostTag>createSet("postTags", PostTag.class, QPostTag.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.study.velog.domain.member.QMember(forProperty("member")) : null;
    }

}

