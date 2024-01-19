package com.study.velog.api.service.member;

import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
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

    @Test
    @DisplayName("멤버 생성")
    void join()
    {
        //given
        CreateMemberServiceRequest request = CreateMemberServiceRequest.builder()
                .email("memberA@gmail.com")
                .nickname("memberA")
                .build();

        //when
        Long memberId = memberService.join(request);
        List<Member> members = memberRepository.findAll();
        Member member = members.get(0);

        //then
        assertThat(members).hasSize(1);
        assertThat(memberId).isEqualTo(member.getMemberId());
        assertThat(member).extracting(Member::getNickname, Member::getEmail)
                .contains("memberA", "memberA@gmail.com");
    }

    @Test
    @DisplayName("닉네임 업데이트")
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
    @DisplayName("멤버 삭제")
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
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members).isEmpty();
    }
}