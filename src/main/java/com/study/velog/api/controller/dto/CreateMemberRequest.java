package com.study.velog.api.controller.dto;

import com.study.velog.api.service.dto.CreateMemberServiceRequest;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record CreateMemberRequest (
        @Email(message = "이메일 형식이 아닙니다.")
        String email,
        String nickname
) {
    public static CreateMemberServiceRequest toServcieDto(CreateMemberRequest request)
    {
        return CreateMemberServiceRequest.builder()
                .email(request.email)
                .nickname(request.nickname)
                .build();
    }
}
