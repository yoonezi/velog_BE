package com.study.velog.api.service;

import com.study.velog.api.service.dto.CreateMemberServiceRequest;
import com.study.velog.api.service.dto.UpdateMemberServiceRequest;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(CreateMemberServiceRequest request)
    {
        validateDuplicateMember(request);
        Member member = Member.builder()
                .email(request.email())
                .nickname(request.nickname())
                .build();

        return memberRepository.save(member).getMemberId();
    }

    public Long updateMember(UpdateMemberServiceRequest request)
    {
        Member member = memberRepository.findById(request.memberId()).orElseThrow(
                () -> new ApiException(ErrorCode.MEMBER_NOT_FOUND)
        );
        member.update(request.email(), request.nickname());
        return member.getMemberId();
    }

    public void deleteMember(Long memberId)
    {
        Member member = memberRepository.findById(memberId).orElseThrow();
        memberRepository.delete(member);
    }

    public void validateDuplicateMember(CreateMemberServiceRequest request)
    {
        if( memberRepository.existsByEmail(request.email()))
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
