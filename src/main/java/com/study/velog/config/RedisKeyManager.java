package com.study.velog.config;

public class RedisKeyManager {

    public static final String POST_FEED_KEY_PREFIX = "feed:post:";
    public static final String FOLLOW_FEED_KEY_PREFIX = "feed:follow:";

    public static String generatePostFeedKey(Long memberId)
    {
        return POST_FEED_KEY_PREFIX + memberId;
    }

    public static String generateFollowFeedKey(Long memberId)
    {
        return FOLLOW_FEED_KEY_PREFIX + memberId;
    }
}
