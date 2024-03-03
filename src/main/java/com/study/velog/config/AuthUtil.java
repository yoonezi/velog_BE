package com.study.velog.config;

import com.study.velog.domain.member.MemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static String currentUserEmail()
    {
        MemberDTO dto = (MemberDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dto.getEmail();
    }

    public static Long currentUserId()
    {
        MemberDTO dto = (MemberDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(dto.getMemberId());
    }

    public static String username()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
