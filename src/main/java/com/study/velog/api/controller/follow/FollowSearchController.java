package com.study.velog.api.controller.follow;

import com.study.velog.api.controller.follow.dto.response.FollowerResponses;
import com.study.velog.api.controller.follow.dto.response.FollowingResponses;
import com.study.velog.config.AuthUtil;
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

    @GetMapping("/following")
    public FollowingResponses findFollowing()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findFollowing(member.getMemberId());

        Set<Long> followingIds = followers.stream()
                .map(Follow::getFolloweeId)
                .collect(Collectors.toSet());

        List<Member> followingMembers = memberRepository.findAllById(followingIds);

        return FollowingResponses.of(followingMembers);
    }

    @GetMapping("/follower")
    public FollowerResponses findFollower()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findFollower(member.getMemberId());

        Set<Long> followerIds = followers.stream()
                .map(Follow::getFolloweeId)
                .collect(Collectors.toSet());

        List<Member> followerMembers = memberRepository.findAllById(followerIds);

        return FollowerResponses.of(followerMembers);
    }

    @GetMapping("/following/{memberEmail}")
    // 멤버가 팔로우 하는 사람들 찾기 = 팔로잉
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

    //TODO email
    @GetMapping("/follower/{memberEmail}")
    // 멤버가 팔로우 하는 사람들 찾기 = 팔로워
    public FollowerResponses findMemberFollower(@PathVariable String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        List<Follow> followers = followRepository.findFollower(member.getMemberId());

        Set<Long> followerIds = followers.stream()
                .map(Follow::getFolloweeId)
                .collect(Collectors.toSet());

        List<Member> followerMembers = memberRepository.findAllById(followerIds);

        return FollowerResponses.of(followerMembers);
    }
}
