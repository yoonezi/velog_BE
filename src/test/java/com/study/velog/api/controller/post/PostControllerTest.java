package com.study.velog.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.controller.post.dto.request.CreatePostRequest;
import com.study.velog.api.controller.post.dto.request.UpdatePostRequest;
import com.study.velog.api.service.post.PostService;
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


@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void save_invalidTitle() throws Exception
    {
        // given
        CreatePostRequest request = CreatePostRequest.builder()
                .memberId(1L)
                .title("")
                .content("content")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("제목은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }


    @Test
    void update_inValidTitle() throws Exception
    {
        // given
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("")
                .content("content")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("제목은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }



}