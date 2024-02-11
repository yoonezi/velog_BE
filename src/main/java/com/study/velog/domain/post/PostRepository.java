package com.study.velog.domain.post;

import com.study.velog.api.controller.post.dto.response.MainPostLikeCountOrder;
import com.study.velog.domain.type.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p " +
            "left join fetch p.member "+
            "left join fetch p.postTags pt " +
            "left join fetch p.postImageList " +
            "left join fetch pt.tag " +
            "where p.postId = :postId")
    Optional<Post> findPostWithFetch(@Param("postId") Long postId);

    @Query("select p from Post p where p.title like concat ('%', :param, '%')")
    Page<Post> findAllBy(@Param("param") String title, Pageable pageable);

    @Query(value = "select p from Post p " +
            "left join fetch p.postImageList " +
            "left join fetch p.member " +
            "where p.postStatus = 'SERVICED'", countQuery = "select count(p.postId) from Post p where p.postStatus = 'SERVICED'")
    Page<Post> findTrendPosts(Pageable pageable);

    @Query("select new com.study.velog.api.controller.post.dto.response.MainPostLikeCountOrder(" +
            "pl.post.postId, " +
            "count(pl.postLikeId) as ct) " +
            "from PostLike pl RIGHT JOIN pl.post group by pl.post.postId")
    Page<MainPostLikeCountOrder> readAllBy(Pageable pageable);

    @Query("select p from Post p " +
            "left join fetch p.postImageList " +
            "left join fetch p.member")
    Page<Post> findLikePosts(Pageable pageable);

    @Query("select p from Post p " +
            "left join fetch p.postImageList " +
            "left join fetch p.member m " +
            "where m.email = :memberEmail")
    Page<Post> findMyPosts(@Param("memberEmail") String memberEmail, Pageable pageable);

    Optional<List<Post>> findPostByCategoryType(PostCategory categoryType);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.postId = :postId")
    int increaseViewCount(@Param("postId") Long postId);

    @Query("select p from Post p " +
            "left join fetch p.postImageList " +
            "left join fetch p.member m " +
            "where p.postId in :postIds")
    Page<Post> findAllPostIds(@Param("postIds") List<Long> postIds, Pageable pageable);

    @Query("select p from Post p join fetch p.member where p.postStatus = 'SERVICED' and (:category is null or p.categoryType = :category)")
    Page<Post> findAllPosts(Pageable pageable, @Param("category") PostCategory category);
}
