package com.study.velog.api.controller.auth;

import com.study.velog.api.controller.auth.dto.AuthLoginSuccessRequest;
import com.study.velog.api.controller.auth.dto.AuthLoginSuccessResponse;
import com.study.velog.api.service.member.MemberService;
import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Long join(@RequestBody CreateMemberServiceRequest request)
    {
        return memberService.join(request);
    }

    @PostMapping("/login")
    public AuthLoginSuccessResponse login(@RequestBody AuthLoginSuccessRequest request)
    {
        return memberService.login(request);
    }
}