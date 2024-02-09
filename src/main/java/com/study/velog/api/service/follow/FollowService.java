package com.study.velog.api.service.follow;

import com.study.velog.api.service.feed.FollowFeedService;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.follow.Follow;
import com.study.velog.domain.follow.FollowRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final FollowFeedService followFeedService;

    public Long follow(Long followeeId)
    {
        Member followerMember = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Member followeeMember = memberRepository.findById(followeeId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Follow follow = Follow.builder()
                .followerId(followerMember.getMemberId())
                .followeeId(followeeMember.getMemberId())
                .build();

        validate(followerMember, followeeMember);

        followFeedService.createAddFollowFeed(followeeMember.getMemberId());

        followRepository.save(follow);
        return follow.getFolloweeId();
    }

    private void validate(Member followerMember, Member followeeMember)
    {
        if (followerMember.getMemberId().equals(followeeMember.getMemberId()))
        {
            throw new ApiException(ErrorCode.SELF_FOLLOW);
        }

        if (followRepository.existsByFollowerIdAndFolloweeId(followerMember.getMemberId(), followeeMember.getMemberId())
        ) {
            throw new ApiException(ErrorCode.ALREADY_FOLLOW);
        }
    }


    public void unFollow(Long followeeId)
    {
        Member followerMember = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Member followeeMember = memberRepository.findById(followeeId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Optional<Follow> presentFollow = followRepository.findByFollowerIdAndFolloweeId(followerMember.getMemberId(), followeeMember.getMemberId());
        if(presentFollow.isEmpty())
        {
            throw new ApiException(ErrorCode.NOT_FOUND_FOLLOW);
        }

        followRepository.deleteByFollowerIdAndFolloweeId(followerMember.getMemberId(), followeeMember.getMemberId());
    }
}
