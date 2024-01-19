package com.study.velog.api.controller.member.dto;

import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UpdateMemberRequest (
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @NotEmpty(message = "닉네임은 필수입니다.")
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
