package com.study.velog.api.controller.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.follow.Follow;
import com.study.velog.domain.follow.FollowRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.member.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.MockedStatic;
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

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FollowStatusController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class FollowStatusControllerTest {

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    FollowRepository followRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private MockedStatic<AuthUtil> authUtilMockedStatic;

    @BeforeEach
    void beforeEach()
    {
        authUtilMockedStatic = mockStatic(AuthUtil.class);
    }

    @Test
    void findFollowStatus() throws Exception
    {
        // given
        Member member = Member.builder()
                .memberId(1L)
                .email("a@gmail.com")
                .nickname("nickname")
                .memberRoles(List.of(MemberRole.ADMIN))
                .build();

        BDDMockito.given(AuthUtil.currentUserEmail()).willReturn("test");

        BDDMockito.given(memberRepository.findByEmail(Mockito.any()))
                .willReturn(Optional.of(member));

        BDDMockito.given(followRepository.findByFollowerIdAndFolloweeId(Mockito.any(), Mockito.any()))
                .willReturn(Optional.of(Follow.builder()
                        .followerId(1L)
                        .followeeId(2L)
                        .build())
                );

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/follow/status/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followerId").value("1"))
                .andExpect(jsonPath("$.followeeId").value("2"))
                .andExpect(jsonPath("$.followStatus").value("FOLLOW"));

    }
}