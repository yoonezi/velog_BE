package com.study.velog.api.controller.follow;

import com.study.velog.api.controller.follow.dto.response.FollowCountResponses;
import com.study.velog.api.controller.follow.dto.response.FollowerResponses;
import com.study.velog.api.controller.follow.dto.response.FollowingResponses;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.follow.Follow;
import com.study.velog.domain.follow.FollowRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/follow/search")
public class FollowSearchController {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/followCount/{memberEmail}")
    public FollowCountResponses findFollowCount(@PathVariable String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        int followerId = followRepository.countByFollowerId(member.getMemberId());
        int followingId = followRepository.countByFolloweeId(member.getMemberId());

        return FollowCountResponses.of(member.getMemberId(), memberEmail ,followerId, followingId);
    }

    @GetMapping("/following/{memberEmail}")
    public FollowingResponses findMemberFollowing(@PathVariable String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findFollowing(member.getMemberId());

        Set<Long> followingIds = followers.stream()
                .map(Follow::getFolloweeId)
                .collect(Collectors.toSet());

        List<Member> followingMembers = memberRepository.findAllById(followingIds);
        return FollowingResponses.of(followingMembers);
    }

    @GetMapping("/follower/{memberEmail}")
    public FollowerResponses findMemberFollower(@PathVariable String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findFollower(member.getMemberId());

        Set<Long> followerIds = followers.stream()
                .map(Follow::getFollowerId)
                .collect(Collectors.toSet());

        List<Member> followerMembers = memberRepository.findAllById(followerIds);
        return FollowerResponses.of(followerMembers);
    }
}
