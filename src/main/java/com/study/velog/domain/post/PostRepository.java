package com.study.velog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p " +
            "left join fetch p.member "+
            "left join fetch p.postTags pt "+
            "left join fetch pt.tag " +
            "where p.postId = :postId")
    Optional<Post> findPostWithFetch(@Param("postId") Long postId);

    @Query("select p from Post p where p.title like concat ('%', :param, '%')")
    Page<Post> findAllBy(@Param("param") String title, Pageable pageable);

    @Query("select p from Post p left join fetch p.postImageList " +
            "left join fetch p.member")
    Page<Post> findMainPosts(Pageable pageable);
}
