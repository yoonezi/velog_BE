package com.study.velog.api.service.follow.dto.request;

import lombok.Builder;

@Builder
public record FollowServiceRequest(
        String followeeEmail

) {
}
