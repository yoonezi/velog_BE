package com.study.velog.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.post.*;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.type.PostCategory;
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
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostSearchController.class)
class PostSearchControllerTest {

    @MockBean
    PostRepository postRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void searchPost() throws Exception
    {
        // given
        BDDMockito.given(postRepository.findPostWithFetch(Mockito.any()))
                .willReturn(
                        Optional.of(
                            Post.builder()
                                    .postId(1L)
                                    .member(Member.builder()
                                            .memberId(1L)
                                            .email("a@gmail.com")
                                            .nickname("nickname")
                                            .build())
                                    .postStatus(PostStatus.SERVICED)
                                    .content("content")
                                    .title("title")
                                    .postImageList(Set.of(
                                            PostImage.builder().postImageId(1L).url("url1").imageOrder(1).build(),
                                            PostImage.builder().postImageId(2L).url("url2").imageOrder(2).build()))
                                    .categoryType(PostCategory.AI)
                                    .postTags(Set.of(PostTag.builder()
                                            .postTagId(1L)
                                            .tag(Tag.builder().tagId(1L).tagContent("tag1").build())
                                            .build()))
                                    .build()
                        )
                );
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/search/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("content"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.categoryType").value("AI"))
                .andExpect(jsonPath("$.tagList[0]").value("tag1"))
        ;
    }
}