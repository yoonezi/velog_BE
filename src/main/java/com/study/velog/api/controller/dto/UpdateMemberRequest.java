package com.study.velog.api.controller.dto;

import com.study.velog.api.service.dto.UpdateMemberServiceRequest;
import lombok.Builder;

@Builder
public record UpdateMemberRequest (
        Long memberId,
        String email,
        String nickname
) {
    public static UpdateMemberServiceRequest toServiceDto(UpdateMemberRequest request)
    {
        return UpdateMemberServiceRequest.builder()
                .memberId(request.memberId())
                .email(request.email)
                .nickname(request.nickname)
                .build();
    }
}
