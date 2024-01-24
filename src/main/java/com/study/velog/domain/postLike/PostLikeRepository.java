package com.study.velog.domain.postLike;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query("select pl from PostLike  pl where pl.post.postId = :postId")
    Page<PostLike> findByPostIdWithPage(@Param("postId") Long postId, Pageable pageable);

//    @Query("select pl from PostLike pl " +
//            "join pl.post " +
//            "where pl.post.postId = :postId " +
//            "and pl.member.memberId = :memberId")
//    Optional<PostLike> findByPostLike(@Param("postId") Long postId, @Param("memberId") Long memberId);

    @Query("select pl from PostLike pl " +
            "join pl.post " +
            "where pl.post.postId = :postId " +
            "and pl.member.email = :email")
    Optional<PostLike> findByPostLike(@Param("postId") Long postId, @Param("email") String email);

}
