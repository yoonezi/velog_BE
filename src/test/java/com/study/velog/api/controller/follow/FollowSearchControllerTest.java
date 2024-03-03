package com.study.velog.api.controller.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.follow.FollowRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberRole;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FollowSearchController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class FollowSearchControllerTest {

    @MockBean
    FollowRepository followRepository;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findMemberFollowing() throws Exception
    {
        Member member = Member.builder()
                        .memberId(1L)
                        .email("a@gmail.com")
                        .nickname("nickname")
                        .memberRoles(List.of(MemberRole.ADMIN))
                        .build();

        BDDMockito.given(memberRepository.findByEmail(Mockito.any()))
                .willReturn(Optional.of(member));

        BDDMockito.given(memberRepository.findAllById(Mockito.any()))
                .willReturn(Lists.newArrayList(
                        Member.builder()
                                .email("yz1@kakao.com")
                                .nickname("yz1")
                                .memberId(1L)
                                .build(),
                        Member.builder()
                                .email("yz2@kakao.com")
                                .nickname("yz2")
                                .memberId(2L)
                                .build()
                ));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/follow/search/following/a@gmail.com")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followingResponses[0].memberId").value("1"))
                .andExpect(jsonPath("$.followingResponses[0].nickname").value("yz1"))
                .andExpect(jsonPath("$.followingResponses[0].email").value("yz1@kakao.com"))
        ;
    }

    @Test
    void findMemberFollower() throws Exception
    {
        Member member = Member.builder()
                .memberId(1L)
                .email("a@gmail.com")
                .nickname("nickname")
                .memberRoles(List.of(MemberRole.ADMIN))
                .build();

        BDDMockito.given(memberRepository.findByEmail(Mockito.any()))
                .willReturn(Optional.of(member));

        BDDMockito.given(memberRepository.findAllById(Mockito.any()))
                .willReturn(Lists.newArrayList(
                        Member.builder()
                                .email("yz1@kakao.com")
                                .nickname("yz1")
                                .memberId(1L)
                                .build(),
                        Member.builder()
                                .email("yz2@kakao.com")
                                .nickname("yz2")
                                .memberId(2L)
                                .build()
                ));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/follow/search/following/a@gmail.com")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followingResponses[0].memberId").value("1"))
                .andExpect(jsonPath("$.followingResponses[0].nickname").value("yz1"))
                .andExpect(jsonPath("$.followingResponses[0].email").value("yz1@kakao.com"))
        ;
    }
}