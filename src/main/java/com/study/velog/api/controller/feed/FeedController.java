package com.study.velog.api.controller.feed;

import com.study.velog.api.controller.feed.dto.response.UserFeedResponse;
import com.study.velog.api.service.feed.FollowFeed;
import com.study.velog.api.service.feed.FollowFeedService;
import com.study.velog.api.service.feed.PostFeed;
import com.study.velog.api.service.feed.PostFeedService;
import com.study.velog.config.AuthUtil;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@CrossOrigin("*")
public class FeedController {

    private final PostFeedService postFeedService;
    private final FollowFeedService followFeedService;
    private final MemberRepository memberRepository;

    @GetMapping
    public UserFeedResponse getMemberPostFeeds()
    {
        List<PostFeed> memberPostFeeds = postFeedService.findMemberPostFeeds(AuthUtil.currentUserId());
        List<FollowFeed> memberFollowFeeds = followFeedService.findMemberFollowFeeds(AuthUtil.currentUserId());

        Set<Long> postMembers = memberPostFeeds.stream()
                .map(PostFeed::memberId)
                .collect(Collectors.toSet());

        Set<Long> followMembers = memberFollowFeeds.stream()
                .map(FollowFeed::memberId)
                .collect(Collectors.toSet());

        postMembers.addAll(followMembers);

        List<Member> members = memberRepository.findAllById(postMembers)
                .stream().filter(s-> s.getMemberStatus().equals(MemberStatus.SERVICED))
                .collect(Collectors.toList());
        return UserFeedResponse.of(memberPostFeeds, memberFollowFeeds, members);
    }
}
