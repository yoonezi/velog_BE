package com.study.velog.api.service.member;

import com.study.velog.api.service.member.dto.response.MemberServiceResponse;
import com.study.velog.api.service.member.dto.response.MemberServiceResponses;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public MemberServiceResponse searchMember(Long memberId)
    {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return MemberServiceResponse.of(member);
    }

    public MemberServiceResponses searchAllMember()
    {
        List<Member> members = memberRepository.findAll();
        return MemberServiceResponses.toList(MemberServiceResponses.of(members));
    }
}
