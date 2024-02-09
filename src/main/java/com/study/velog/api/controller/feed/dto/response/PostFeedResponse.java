package com.study.velog.api.controller.feed.dto.response;

import com.study.velog.api.service.feed.PostFeed;
import lombok.Builder;

import java.util.List;

@Builder
public record PostFeedResponse (
        List<PostFeed> postFeedList
) {

}
