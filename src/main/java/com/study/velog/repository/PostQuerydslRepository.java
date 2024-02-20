package com.study.velog.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.velog.api.controller.post.dto.request.PostSearchCondition;
import com.study.velog.api.controller.post.dto.request.PostSortType;
import com.study.velog.api.controller.post.dto.response.MainPostsResponse;
import com.study.velog.api.controller.post.dto.response.MyPostResponse;
import com.study.velog.api.controller.post.dto.response.PostImageResponse;
import com.study.velog.api.controller.post.dto.response.PostResponses;
import com.study.velog.domain.member.QMember;
import com.study.velog.domain.post.*;
import com.study.velog.domain.tag.QTag;
import com.study.velog.domain.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;

@Component
@RequiredArgsConstructor
public class PostQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<PostResponses> findAllPosts(PostSearchCondition condition)
    {
        QPost post = QPost.post;
        QMember member = QMember.member;
        QPostImage postImage = QPostImage.postImage;

        List<Long> postIds = jpaQueryFactory.select(post.postId)
                .from(post)
                .join(post.member, member)
                .where(
                        serviced(),
                        categoryEq(condition.getPostCategory()),
                        memberIdEq(condition.getMemberId())
                )
                .limit(condition.getPageable().getPageSize())
                .offset(condition.getPageable().getOffset())
                .orderBy(createOrderSpecifier(condition.getPostSortType()))
                .fetch();

        long total = jpaQueryFactory.select(post.count())
                .from(post)
                .join(post.member, member)
                .where(
                        serviced(),
                        categoryEq(condition.getPostCategory()),
                        memberIdEq(condition.getMemberId())
                )
                .fetchOne();

        List<PostResponses> response = jpaQueryFactory.selectFrom(post)
                .join(post.member, member)
                .leftJoin(post.postImageList, postImage)
                .where(post.postId.in(postIds))
                .distinct()
                .transform(
                        groupBy(post.postId)
                                .list(
                                        Projections.constructor(
                                                PostResponses.class,
                                                post.postId,
                                                post.title,
                                                post.content,
                                                post.viewCount,
                                                post.categoryType,
                                                post.registerDate,
                                                post.member.memberId,
                                                post.member.nickname,
                                                post.postStatus,
                                                GroupBy.set(
                                                        Projections.constructor(
                                                                PostImageResponse.class,
                                                                postImage.url,
                                                                postImage.imageOrder
                                                        )
                                                )
                                        )
                                ));

        return new PageImpl<>(response, condition.getPageable(), total);
    }

    private BooleanExpression memberIdEq(Long memberId)
    {
        return memberId != null ? QPost.post.member.memberId.eq(memberId) : null;
    }

    public MainPostsResponse findPosts(PostSearchCondition condition)
    {
        QPost post = QPost.post;
        QMember member = QMember.member;

        List<Post> posts = jpaQueryFactory.select(post)
                .from(post)
                .join(post.member, member).fetchJoin()
                .where(
                        serviced(),
                        categoryEq(condition.getPostCategory())
                )
                .limit(condition.getPageable().getPageSize())
                .offset(condition.getPageable().getOffset())
                .orderBy(createOrderSpecifier(condition.getPostSortType()))
                .fetch();

        return MainPostsResponse.of(posts);
    }

    public MyPostResponse findMyPosts(PostSearchCondition condition)
    {
        Page<PostResponses> response = findAllPosts(condition);
        return MyPostResponse.of(response);
    }

    private BooleanExpression categoryEq(PostCategory postCategory)
    {
        return postCategory != null ? QPost.post.categoryType.eq(postCategory) : null;
    }

    private BooleanExpression serviced()
    {
        return QPost.post.postStatus.eq(PostStatus.SERVICED);
    }

    private OrderSpecifier createOrderSpecifier(PostSortType sortType)
    {
        return switch (sortType) {
            case LATEST -> new OrderSpecifier<>(Order.DESC, QPost.post.registerDate);
            case VIEWS -> new OrderSpecifier<>(Order.DESC, QPost.post.viewCount);
            default -> new OrderSpecifier<>(Order.DESC, QPost.post.postId);
        };
    }

    public Optional<Post> findPostWithFetch(Long postId)
    {
        QPost post = QPost.post;
        QMember member = QMember.member;
        QPostTag postTags = QPostTag.postTag;
        QTag tag = QTag.tag;
        QPostImage postImage = QPostImage.postImage;

        Post result = jpaQueryFactory.selectFrom(post)
                .leftJoin(post.member, member).fetchJoin()
                .leftJoin(post.postTags, postTags).fetchJoin()
                .leftJoin(post.postImageList, postImage).fetchJoin()
                .leftJoin(postTags.tag, tag).fetchJoin()
                .where(post.postId.eq(postId).and(post.postStatus.eq(PostStatus.SERVICED)))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
