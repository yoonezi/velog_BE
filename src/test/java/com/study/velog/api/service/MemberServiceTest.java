package com.study.velog.api.service;

import com.study.velog.api.service.dto.CreateMemberServiceRequest;
import com.study.velog.api.service.dto.UpdateMemberServiceRequest;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("멤버 서비스 테스트")
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("멤버 생성")
    @Test
    void join()
    {
        //given
        CreateMemberServiceRequest request = CreateMemberServiceRequest.builder()
                .email("memberA@gmail.com")
                .nickname("memberA")
                .build();

        //when
        Long memberId = memberService.join(request);

        //then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        Member member = members.get(0);
        assertThat(memberId).isEqualTo(member.getMemberId());
        assertThat(member).extracting(Member::getNickname, Member::getEmail)
                .contains("memberA", "memberA@gmail.com");
    }

    @DisplayName("닉네임 업데이트")
    @Test
    void updateMember()
    {
        //given
        String email = "memberA@gmail.com";
        Member member = memberRepository.save(
                Member.builder()
                        .email(email)
                        .nickname("memberA")
                        .build());

        UpdateMemberServiceRequest request = UpdateMemberServiceRequest.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname("memberB")
                .build();

        //when
        Long memberId = memberService.updateMember(request);
        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        //then
        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.getNickname()).isEqualTo("memberB");
    }

    @Test
    void deleteMember()
    {
        //given
        Member member = memberRepository.save(
                Member.builder()
                        .email("memberA@gmail.com")
                        .nickname("memberA")
                        .build());

        //when
        memberService.deleteMember(member.getMemberId());
        List<Member> memberList = memberRepository.findAll();

        //then
        assertThat(memberList).isEmpty();
    }
}