package com.study.velog.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MemberSearchController.class)
class MemberSearchControllerTest {

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
}