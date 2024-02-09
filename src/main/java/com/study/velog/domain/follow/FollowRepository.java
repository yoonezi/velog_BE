package com.study.velog.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);


    // 내가 팔로우 하는 사람들 가져오기 = 팔로잉
    @Query("select f FROM Follow f WHERE f.followerId = :memberId")
    List<Follow> findFollowing(@Param("memberId") Long memberId);

    // 나를 팔로우 하는 사람들 가져오기 = 팔로워
    @Query("select f FROM Follow f WHERE f.followeeId = :memberId")
    List<Follow> findFollower(@Param("memberId") Long memberId);
}
