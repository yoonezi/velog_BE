package com.study.velog.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
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

import java.util.Optional;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MemberSearchController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class MemberSearchControllerTest {

    @MockBean
    MemberRepository memberRepository;

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
    void searchMember() throws Exception
    {
        // given
        Optional<Member> member = Optional.of(
                Member.builder()
                        .memberId(1L)
                        .email("a@gmail.com")
                        .nickname("nickname")
                        .build()
        );
        BDDMockito.given(AuthUtil.currentUserEmail()).willReturn("test");
        BDDMockito.given(memberRepository.findById(Mockito.any()))
                        .willReturn(member);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/search/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("1"))
                .andExpect(jsonPath("$.email").value("a@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("nickname"));
    }

    @Test
    void searchMemberByEmail_Success() throws Exception {
        // given
        Optional<Member> member = Optional.of(
                Member.builder()
                        .memberId(1L)
                        .email("a@gmail.com")
                        .nickname("nickname")
                        .build()
        );

        BDDMockito.given(AuthUtil.currentUserEmail()).willReturn("test");
        BDDMockito.given(memberRepository.findByEmail(Mockito.any())).willReturn(member);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/search/email")
                        .param("email", "a@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("1"))
                .andExpect(jsonPath("$.email").value("a@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("nickname"));
    }
}