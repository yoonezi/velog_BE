//package com.study.velog.domain.follow;
//
//import com.study.velog.config.exception.ApiException;
//import com.study.velog.config.exception.ErrorCode;
//import com.study.velog.domain.member.Member;
//import com.study.velog.domain.member.MemberRepository;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional
//@SpringBootTest
//class FollowRepositoryTest {
//
//    @Autowired
//    FollowRepository followRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    EntityManager entityManager;
//
//    @Test
//    void findByFollowerEmailAndFolloweeId ()
//    {
//        // given
//        Member follower = memberRepository.save(Member
//                .create("follower@gmail.com", "follower"));
//        Member followee = memberRepository.save(Member
//                .create("followee@gmail.com", "followee"));
//
//        Follow follow = followRepository.save(Follow.builder()
//                .followerEmail(follower.getEmail())
//                .followeeId(followee.getMemberId())
//                .build());
//
//        entityManager.flush();
//        entityManager.clear();
//
//        // when
//        Follow findFollow = followRepository.findByFollowerEmailAndFolloweeId(follower.getEmail(), followee.getMemberId())
//                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_FOLLOW));
//
//        // then
//        assertThat(findFollow.getFollowerEmail()).isEqualTo(follower.getEmail());
//        assertThat(findFollow.getFolloweeId()).isEqualTo(followee.getMemberId());
//    }
//
//    @Test
//    void deleteByFollowerEmailAndFolloweeId() {
//        // given
//        Member follower = memberRepository.save(Member
//                .create("follower@gmail.com", "a"));
//        Member followee = memberRepository.save(Member
//                .create("followee@gmail.com", "b"));
//
//        Follow follow = followRepository.save(Follow.builder()
//                .followerEmail(follower.getEmail())
//                .followeeId(followee.getMemberId())
//                .build());
//
//        entityManager.flush();
//        entityManager.clear();
//
//        // when
//        followRepository.deleteByFollowerEmailAndFolloweeId(follower.getEmail(), followee.getMemberId());
//
//        // then
//        List<Follow> findFollow = followRepository.findAll();
//        assertThat(findFollow).isEmpty();
//    }
//}