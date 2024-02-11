package com.study.velog.api.controller.member;

import com.study.velog.api.controller.member.dto.response.MemberResponse;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/member/search")
@CrossOrigin("*")
public class MemberSearchController {

    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}")
    public MemberResponse searchMember(@PathVariable Long memberId)
    {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getMemberStatus() == MemberStatus.DELETED)
        {
            throw new ApiException(ErrorCode.POST_STATUS_DELETED);
        }

        return MemberResponse.of(member);
    }

    @GetMapping("/email")
    public MemberResponse searchMemberByEmail()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getMemberStatus() == MemberStatus.DELETED)
        {
            throw new ApiException(ErrorCode.POST_STATUS_DELETED);
        }

        return MemberResponse.of(member);
    }
}
