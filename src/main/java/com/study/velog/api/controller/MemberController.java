package com.study.velog.api.controller;

import com.study.velog.api.controller.dto.CreateMemberRequest;
import com.study.velog.api.controller.dto.UpdateMemberRequest;
import com.study.velog.api.service.MemberService;
import com.study.velog.api.service.dto.CreateMemberServiceRequest;
import com.study.velog.api.service.dto.UpdateMemberServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Long join(@Valid @RequestBody CreateMemberRequest request)
    {
        CreateMemberServiceRequest serviceDto = CreateMemberRequest.toServcieDto(request);
        return memberService.join(serviceDto);
    }

    @PutMapping
    public Long updateMember(@Valid @RequestBody UpdateMemberRequest request)
    {
        UpdateMemberServiceRequest serviceDto = UpdateMemberRequest.toServiceDto(request);
        return memberService.updateMember(serviceDto);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId)
    {
        memberService.deleteMember(memberId);
    }
}
