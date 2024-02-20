package com.study.velog.api.service.feed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class PostFeedServiceTest {

    @Autowired
    private RedisTemplate<String, PostFeed> postFeedTemplate;

    @Test
    void dddd()
    {
//        postFeedTemplate.opsForList().leftPush("postfeed:1", new PostFeed(1L, "ddddd@mccm.com", FeedTaskType.POST_LIKE, LocalDateTime.now()));
        List<PostFeed> range = postFeedTemplate.opsForList().range("postfeed:1", 0, 0);
    }
}