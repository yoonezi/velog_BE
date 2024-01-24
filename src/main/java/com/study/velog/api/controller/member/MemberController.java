package com.study.velog.api.controller.member;

import com.study.velog.api.controller.member.dto.CreateMemberRequest;
import com.study.velog.api.controller.member.dto.UpdateMemberRequest;
import com.study.velog.api.service.member.MemberService;
import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
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
        CreateMemberServiceRequest serviceDto = request.toServiceDto();
        return memberService.join(serviceDto);
    }

    // TODO 확인
    @PutMapping()
    public Long updateMember(@Valid @RequestBody UpdateMemberRequest request)
    {
        UpdateMemberServiceRequest serviceDto = request.toServiceDto();
        return memberService.updateMember(serviceDto);
    }

    // TODO 확인
    @DeleteMapping()
    public void deleteMember()
    {
        memberService.deleteMember();
    }
}
