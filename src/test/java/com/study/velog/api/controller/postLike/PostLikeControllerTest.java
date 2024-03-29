package com.study.velog.api.controller.postLike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.controller.postLike.dto.request.CreatePostLikeRequest;
import com.study.velog.api.service.postLike.PostLikeService;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.security.filter.JWTCheckFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
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

import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostLikeController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class PostLikeControllerTest {

    @MockBean
    PostLikeService postLikeService;

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
    void like() throws Exception
    {
        // given
        CreatePostLikeRequest request = CreatePostLikeRequest.builder()
                .postId(1L)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postLike/1")
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk());
    }
}