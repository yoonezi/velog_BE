package com.study.velog.api.service;

import com.study.velog.api.service.dto.response.MemberResponse;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public MemberResponse searchMember(Long memberId)
    {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return MemberResponse.of(member);
    }

    public List<MemberResponse> searchAllMember()
    {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }
}
