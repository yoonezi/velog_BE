package com.study.velog.api.controller.member;

import com.study.velog.api.controller.member.dto.response.MemberResponse;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/member/search")
public class MemberSearchController {

    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}")
    public MemberResponse searchMember(@PathVariable Long memberId)
    {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponse.of(member);
    }
}
