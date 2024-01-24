package com.study.velog.api.controller.member.dto;

import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UpdateMemberRequest (
        @NotEmpty(message = "닉네임은 필수입니다.")
        String nickname
) {
    public UpdateMemberServiceRequest toServiceDto()
    {
        return UpdateMemberServiceRequest.builder()
                .nickname(this.nickname)
                .build();
    }
}
