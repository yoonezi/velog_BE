package com.study.velog.config;

import com.study.velog.api.service.feed.FollowFeed;
import com.study.velog.api.service.feed.PostFeed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory()
    {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, PostFeed> postFeedTemplate()
    {
        RedisTemplate<String, PostFeed> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new PostFeedValueSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, FollowFeed> followFeedTemplate()
    {
        RedisTemplate<String, FollowFeed> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FollowFeedValueSerializer());
        return redisTemplate;
    }
}
