package com.study.velog.api.controller.dto;

import com.study.velog.api.service.dto.UpdateMemberServiceRequest;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record UpdateMemberRequest (
        @Email
        String email,
        String nickname
) {
    public UpdateMemberServiceRequest toServiceDto(Long memberId, UpdateMemberRequest request)
    {
        return UpdateMemberServiceRequest.builder()
                .memberId(memberId)
                .email(request.email)
                .nickname(request.nickname)
                .build();
    }
}
