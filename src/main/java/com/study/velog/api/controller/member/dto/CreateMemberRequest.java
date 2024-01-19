package com.study.velog.api.controller.member.dto;

import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateMemberRequest (
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname
) {
    public CreateMemberServiceRequest toServiceDto()
    {
        return CreateMemberServiceRequest.builder()
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
