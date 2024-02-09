package com.study.velog.api.service.feed;

import com.study.velog.config.AuthUtil;
import com.study.velog.config.RedisKeyManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 1. 내가 쓴 포스트에 다른사람이 댓글을 성생하면 피드 보낸다.
 * 2. 내가 쓴 포스트에 다른사람이 좋아요 했을 때 피드 보낸다.
 */
@Service
public class PostFeedService {

    public static final int FEED_SIZE = 10;
    private final RedisTemplate<String, PostFeed> postFeedTemplate;

    public PostFeedService(@Qualifier("postFeedTemplate") RedisTemplate<String, PostFeed> postFeedTemplate)
    {
        this.postFeedTemplate = postFeedTemplate;
    }

    public void createAddCommentPostFeed(long postId, long memberId)
    {
        postFeedTemplate.opsForList()
                .leftPush(RedisKeyManager.generatePostFeedKey(memberId),
                        PostFeed.of(postId, AuthUtil.currentUserEmail() ,FeedTaskType.ADD_COMMENT));
    }

    public void createPostLikePostFeed(long postId, long memberId)
    {
        postFeedTemplate.opsForList()
                .leftPush(RedisKeyManager.generatePostFeedKey(memberId),
                        PostFeed.of(postId, AuthUtil.currentUserEmail(), FeedTaskType.POST_LIKE));
    }

    public List<PostFeed> findMemberPostFeeds(Long memberId)
    {
        return postFeedTemplate.opsForList()
                .range(RedisKeyManager.generatePostFeedKey(memberId), 0, FEED_SIZE);
    }

}
