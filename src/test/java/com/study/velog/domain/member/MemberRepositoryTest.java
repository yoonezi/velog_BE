package com.study.velog.domain.member;

import com.google.common.collect.Lists;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findByEmail() {
        // Given
        String email = "test@gmail.com";

        Member member = Member.builder()
                .email(email)
                .nickname("test")
                .memberStatus(MemberStatus.SERVICED)
                .password("aaa")
                .memberRoles(Lists.newArrayList(MemberRole.ADMIN))
                .build();

        memberRepository.save(member);

        // When
        Optional<Member> findMember = memberRepository.findByEmail(email);

        // Then
        assertThat(findMember.get().getEmail()).isEqualTo(email);
    }

    @Test
    void existsByEmail() {
        // Given
        String email = "test@gmail.com";

        Member member = Member.builder()
                .email(email)
                .nickname("test")
                .memberStatus(MemberStatus.SERVICED)
                .password("aaa")
                .memberRoles(Lists.newArrayList(MemberRole.ADMIN))
                .build();

        memberRepository.save(member);

        // When
        boolean exists = memberRepository.existsByEmail(email);

        // Then
        assertThat(exists).isTrue();

    }

    @Test
    void getWithRoles() {
        // Given
        String email = "test@gmail.com";

        Member member = Member.builder()
                .email(email)
                .nickname("test")
                .memberStatus(MemberStatus.SERVICED)
                .password("aaa")
                .memberRoles(Lists.newArrayList(MemberRole.ADMIN))
                .build();
        memberRepository.save(member);

        // When
        Member foundMember = memberRepository.getWithRoles(email);

        // Then
        assertThat(foundMember.getMemberRoles()).isEqualTo(Lists.newArrayList(MemberRole.ADMIN));
    }



}