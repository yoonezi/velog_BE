package com.study.velog.api.service;

import com.study.velog.api.service.dto.response.MemberResponse;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberSearchServiceTest {

    @Autowired
    MemberSearchService memberSearchService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void searchMember()
    {
        //given
        Member memberA = memberRepository.save(
                Member.builder()
                        .email("memberA@gmail.com")
                        .nickname("memberA")
                        .build());

        //when
        MemberResponse member = memberSearchService.searchMember(memberA.getMemberId());

        //then
        assertThat(member.email()).isEqualTo(memberA.getEmail());
        assertThat(member.nickname()).isEqualTo(memberA.getNickname());
    }

    @Test
    void searchAllMember()
    {
        //given
        Member memberA = memberRepository.save(
                Member.builder()
                        .email("memberA@gmail.com")
                        .nickname("memberA")
                        .build());

        Member memberB = memberRepository.save(
                Member.builder()
                        .email("memberB@gmail.com")
                        .nickname("memberB")
                        .build());

        //when
        List<MemberResponse> memberList = memberSearchService.searchAllMember();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }
}