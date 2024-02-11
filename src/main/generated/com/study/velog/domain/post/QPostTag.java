package com.study.velog.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostTag is a Querydsl query type for PostTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostTag extends EntityPathBase<PostTag> {

    private static final long serialVersionUID = 140285643L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostTag postTag = new QPostTag("postTag");

    public final com.study.velog.domain.QBaseTimeEntity _super = new com.study.velog.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QPost post;

    public final NumberPath<Long> postTagId = createNumber("postTagId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final com.study.velog.domain.tag.QTag tag;

    public QPostTag(String variable) {
        this(PostTag.class, forVariable(variable), INITS);
    }

    public QPostTag(Path<? extends PostTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostTag(PathMetadata metadata, PathInits inits) {
        this(PostTag.class, metadata, inits);
    }

    public QPostTag(Class<? extends PostTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.tag = inits.isInitialized("tag") ? new com.study.velog.domain.tag.QTag(forProperty("tag")) : null;
    }

}

