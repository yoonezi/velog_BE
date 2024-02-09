package com.study.velog.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.velog.api.service.feed.PostFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class PostFeedValueSerializer implements RedisSerializer<PostFeed> {

    public static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Charset UTF_8 = StandardCharsets.UTF_8;

    @Override
    public byte[] serialize(PostFeed postFeed) throws SerializationException
    {
        if (ObjectUtils.isEmpty(postFeed))
        {
            return null;
        }
        try{
            String json = MAPPER.writeValueAsString(postFeed);
            return json.getBytes(UTF_8);
        }catch (JsonProcessingException e)
        {
            throw new SerializationException("json serialize error", e);
        }
    }

    @Override
    public PostFeed deserialize(byte[] bytes) throws SerializationException
    {
        if (ObjectUtils.isEmpty(bytes))
        {
            return null;
        }

        try
        {
            return MAPPER.readValue(new String(bytes, UTF_8), PostFeed.class);
        }
        catch (JsonProcessingException e)
        {
            throw new SerializationException("json deserialize error", e);
        }
    }
}
