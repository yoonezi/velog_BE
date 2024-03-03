package com.study.velog.api.service.member;

import com.study.velog.api.controller.auth.dto.AuthLoginSuccessRequest;
import com.study.velog.api.controller.auth.dto.AuthLoginSuccessResponse;
import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.config.security.TokenProvider;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberDTO;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public Long join(CreateMemberServiceRequest request)
    {
        validateDuplicateMember(request);
        Member member = Member.join(request.email(), request.nickname(), request.password(), passwordEncoder);
        return memberRepository.save(member).getMemberId();
    }

    public Long updateMember(UpdateMemberServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(member.getEmail(), request.nickname());
        return member.getMemberId();
    }

    public void deleteMember()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        member.delete();
    }

    public void validateDuplicateMember(CreateMemberServiceRequest request)
    {
        if (!request.password().equals(request.confirmPassword()))
        {
            throw new IllegalArgumentException("확인 비밀번호가 다릅니다.");
        }

        if( memberRepository.existsByEmail(request.email()))
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public AuthLoginSuccessResponse login(AuthLoginSuccessRequest request)
    {
        Member member = memberRepository.findByEmail(request.email()).orElseThrow();

        if(member.getMemberStatus() == MemberStatus.DELETED)
        {
            throw new ApiException(ErrorCode.MEMBER_STATUS_DELETED);
        }

        if(!member.matchPassword(request.password(), passwordEncoder))
        {
            throw new RuntimeException("Password 틀림");
        }

        MemberDTO dto = MemberDTO.toDTO(member);
        String accessToken = tokenProvider.generateAccessToken(dto);
        return new AuthLoginSuccessResponse(accessToken, member.getEmail(), member.getNickname(), member.getMemberId());
    }
}
