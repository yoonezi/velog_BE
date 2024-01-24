package com.study.velog.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.controller.member.dto.CreateMemberRequest;
import com.study.velog.api.controller.member.dto.UpdateMemberRequest;
import com.study.velog.api.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void join_invalidEmail() throws Exception
    {
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("agmail.com")
                .nickname("nickname")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("이메일 형식이 아닙니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @Test
    void join_invalidNickName() throws Exception
    {
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("a@gmail.com")
                .nickname("")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("닉네임은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @Test
    void update_invalidNickName() throws Exception
    {
        // given
        UpdateMemberRequest request = UpdateMemberRequest.builder()
                .nickname("")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/member")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("닉네임은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }
}