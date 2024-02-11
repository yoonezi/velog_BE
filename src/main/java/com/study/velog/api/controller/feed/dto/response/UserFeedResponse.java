package com.study.velog.api.controller.feed.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.velog.api.service.feed.FeedTaskType;
import com.study.velog.api.service.feed.FollowFeed;
import com.study.velog.api.service.feed.PostFeed;
import com.study.velog.domain.member.Member;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
public record UserFeedResponse(
        List<UserFeed> userFeeds
) {
    public static UserFeedResponse of(List<PostFeed> memberPostFeeds, List<FollowFeed> memberFollowFeeds, List<Member> members)
    {
        Map<Long, Member> memberMap = members.stream()
                        .collect(Collectors.toMap(Member::getMemberId, s -> s));

        List<UserFeed> postUserFeeds = memberPostFeeds.stream()
                .filter(s-> memberMap.containsKey(s.memberId()))
                .map(s-> {
                    if (s.task().equals(FeedTaskType.POST_LIKE))
                    {
                        return new UserFeed(
                                FeedTaskType.POST_LIKE.toString(),
                                String.format("%s 님이 회원님의 게시글을 좋아요를 눌렀습니다.", memberMap.get(s.memberId()).getNickname()),
                                s.localDateTime()
                        );
                    }
                    else if (s.task().equals(FeedTaskType.ADD_COMMENT)) {
                        return new UserFeed(FeedTaskType.ADD_COMMENT.toString(),
                                String.format("%s 님이 회원님의 게시글의 댓글을 추가하였습니다.", memberMap.get(s.memberId()).getNickname()),
                                s.localDateTime()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<UserFeed> followUserFeeds = memberFollowFeeds.stream()
                .filter(s-> memberMap.containsKey(s.memberId()))
                .map(s-> new UserFeed(
                        FeedTaskType.ADD_FOLLOW.toString(),
                        String.format("%s님이 팔로우 시작함", memberMap.get(s.memberId())),
                        s.localDateTime()
                ))
                .toList();

        postUserFeeds.addAll(followUserFeeds);

        return new UserFeedResponse(
                postUserFeeds.stream()
                .sorted((a, b) -> b.regDate.compareTo(a.regDate))
                .limit(10)
                .collect(Collectors.toList())
        );
    }

    public record UserFeed(
            String feedTaskType,
            String message,
            @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime regDate
    ) {}
}
