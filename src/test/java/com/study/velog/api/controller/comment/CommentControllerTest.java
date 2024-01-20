package com.study.velog.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.controller.comment.dto.request.CreateCommentRequest;
import com.study.velog.api.controller.comment.dto.request.UpdateCommentRequest;
import com.study.velog.api.service.comment.CommentService;
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

@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @MockBean
    CommentService CommentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_invalidContent() throws Exception
    {
        // given
        CreateCommentRequest request = CreateCommentRequest.builder()
                .content("")
                .memberId(1L)
                .postId(1L)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("내용은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }


    @Test
    void update_invalidContent() throws Exception
    {
        // given
        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .content("")
                .memberId(1L)
                .postId(1L)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("내용은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }
}