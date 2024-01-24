package com.study.velog.config;

import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInit {

    public static final String MASTER = "MASTER";
    public static final Long MASTER_ID = 1L;
    public static final String MASTER_EMAIL = "MASTER@MASTER.com";

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init()
    {
        memberRepository.save(
                Member.builder()
                        .memberStatus(MemberStatus.SERVICED)
                        .nickname(MASTER)
                        .email(MASTER_EMAIL)
                        .memberId(MASTER_ID)
                        .build()
        );
    }
}
