package com.study.velog.api.service.feed;

import com.study.velog.config.AuthUtil;
import com.study.velog.config.RedisKeyManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 팔로우를 할 때 피드를 보낸다.
 */
@Service
public class FollowFeedService {

    public static final int FEED_SIZE = 10;
    private final RedisTemplate<String, FollowFeed> followFeedTemplate;

    public FollowFeedService(@Qualifier("followFeedTemplate") RedisTemplate<String, FollowFeed> followFeedTemplate)
    {
        this.followFeedTemplate = followFeedTemplate;
    }

    public void createAddFollowFeed(long memberId)
    {
        followFeedTemplate.opsForList()
                .leftPush(RedisKeyManager.generateFollowFeedKey(memberId),
                        FollowFeed.of(AuthUtil.currentUserEmail(), memberId, FeedTaskType.ADD_FOLLOW));
    }

    public List<FollowFeed> findMemberFollowFeeds(Long memberId)
    {
        return followFeedTemplate.opsForList()
                .range(RedisKeyManager.generateFollowFeedKey(memberId), 0, FEED_SIZE);
    }

}
