package com.study.velog.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.controller.comment.dto.request.CreateCommentRequest;
import com.study.velog.api.controller.comment.dto.request.UpdateCommentRequest;
import com.study.velog.api.service.comment.CommentService;
import com.study.velog.config.security.filter.JWTCheckFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CommentController.class,
excludeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
})
@WithMockUser
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
                .postId(1L)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
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