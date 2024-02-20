package com.study.velog.domain.follow;

import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FollowRepositoryTest {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findByFollowerEmailAndFolloweeId ()
    {
        // given
        Member follower = memberRepository.save(Member
                .create("follower@gmail.com", "follower"));

        Member followee = memberRepository.save(Member
                .create("followee@gmail.com", "followee"));

        followRepository.save(Follow.builder()
                .followerId(follower.getMemberId())
                .followeeId(followee.getMemberId())
                .build());

        entityManager.flush();
        entityManager.clear();

        // when
        Follow findFollow = followRepository.findByFollowerIdAndFolloweeId(follower.getMemberId(), followee.getMemberId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLLOW));

        // then
        assertThat(findFollow.getFollowerId()).isEqualTo(follower.getMemberId());
        assertThat(findFollow.getFolloweeId()).isEqualTo(followee.getMemberId());
    }

    @Test
    void deleteByFollowerEmailAndFolloweeId() {
        // given
        Member follower = memberRepository.save(Member
                .create("follower@gmail.com", "a"));
        Member followee = memberRepository.save(Member
                .create("followee@gmail.com", "b"));

        followRepository.save(Follow.builder()
                .followerId(follower.getMemberId())
                .followeeId(followee.getMemberId())
                .build());


        entityManager.flush();
        entityManager.clear();

        // when
        followRepository.deleteByFollowerIdAndFolloweeId(follower.getMemberId(), followee.getMemberId());

        // then
        List<Follow> findFollow = followRepository.findAll();
        assertThat(findFollow).isEmpty();
    }
}