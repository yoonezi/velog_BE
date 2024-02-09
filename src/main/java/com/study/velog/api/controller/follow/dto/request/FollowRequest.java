package com.study.velog.api.controller.follow.dto.request;

import lombok.Builder;

@Builder
public record FollowRequest (
        String followeeEmail
){
}
