package com.study.velog.config.security;

import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberDTO;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        log.info("[START] - loadUserByUsername");
        Member member = memberRepository.getWithRoles(username);

        if(member == null)
        {
            throw new UsernameNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getErrorMessage());
        }

        return new MemberDTO(
                member.getEmail(),
                member.getEmail(),
                member.getNickname(),
                member.getMemberId().toString(),
                member.getMemberRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
    }
}