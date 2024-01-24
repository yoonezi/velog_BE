package com.study.velog.api.service.member;

import com.study.velog.api.service.member.dto.request.CreateMemberServiceRequest;
import com.study.velog.api.service.member.dto.request.UpdateMemberServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberStatus;
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
        Member member = members.get(1);

        //then
        assertThat(members).hasSize(2);
        assertThat(memberId).isEqualTo(member.getMemberId());
        assertThat(member).extracting(Member::getNickname, Member::getEmail)
                .contains("memberA", "memberA@gmail.com");
    }

    @Test
    @DisplayName("닉네임 업데이트")
    void updateMember()
    {
        //given
        UpdateMemberServiceRequest request = UpdateMemberServiceRequest.builder()
                .nickname("updatedName")
                .build();

        //when
        Long memberId = memberService.updateMember(request);
        Member findMember = memberRepository.findByEmail(AuthUtil.currentUserEmail()).orElseThrow();

        //then
        assertThat(findMember.getMemberId()).isEqualTo(memberId);
        assertThat(findMember.getNickname()).isEqualTo("updatedName");
    }

    @Test
    @DisplayName("멤버 삭제")
    void deleteMember()
    {
        //when
        memberService.deleteMember();
        Member byMemberStatus = memberRepository.findByMemberStatus(MemberStatus.SERVICED);

        //then
        assertThat(byMemberStatus).isNull();
    }
}