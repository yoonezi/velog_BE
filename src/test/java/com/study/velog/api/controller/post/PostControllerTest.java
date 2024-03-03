package com.study.velog.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.study.velog.api.controller.post.dto.request.CreatePostRequest;
import com.study.velog.api.controller.post.dto.request.UpdatePostRequest;
import com.study.velog.api.service.post.PostService;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.type.PostCategory;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
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
                .title("")
                .content("content")
                .categoryType(PostCategory.AI)
                .tagList(Lists.newArrayList("tag1", "tag2"))
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
    void save_invalidCategoryType() throws Exception
    {
        // given
        CreatePostRequest request = CreatePostRequest.builder()
                .title("title")
                .content("content")
                .categoryType(null)
                .tagList(Lists.newArrayList("tag1", "tag2"))
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("카테고리는 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @Test
    void update_inValidTitle() throws Exception
    {
        // given
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("")
                .content("content")
                .categoryType(PostCategory.AI)
                .tagList(Lists.newArrayList("tag1", "tag2"))
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
    void update_invalidCategoryType() throws Exception
    {
        // given
        CreatePostRequest request = CreatePostRequest.builder()
                .title("title")
                .content("content")
                .categoryType(null)
                .tagList(Lists.newArrayList("tag1", "tag2"))
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("카테고리는 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }
}