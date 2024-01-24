package com.study.velog.api.service.member;

import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
import com.study.velog.config.AuthUtil;
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

    //TODO email로 변경
    public Long join(CreateMemberServiceRequest request)
    {
        validateDuplicateMember(request);
        Member member = Member.create(request.email(), request.nickname());

        return memberRepository.save(member).getMemberId();
    }


    public Long updateMember(UpdateMemberServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(member.getEmail(), request.nickname());
        return member.getMemberId();
    }

    // TODO 비우는 게 맞는지 확인
    public void deleteMember()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        member.delete();
    }

    public void validateDuplicateMember(CreateMemberServiceRequest request)
    {
        if( memberRepository.existsByEmail(request.email()))
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
