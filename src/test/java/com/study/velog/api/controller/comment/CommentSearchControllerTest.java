package com.study.velog.api.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.comment.Comment;
import com.study.velog.domain.comment.CommentRepository;
import com.study.velog.domain.comment.CommentStatus;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostImage;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.post.PostTag;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CommentSearchController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
public class CommentSearchControllerTest {

    @MockBean
    CommentRepository commentRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findComments() throws Exception
    {
        // given
        int page = 0;
        int size = 10;

        BDDMockito.given(commentRepository.findByPostIdWithPage(Mockito.any(), Mockito.any()))
                .willReturn(
                        new PageImpl<>(
                                Lists.newArrayList(
                                        Comment.builder()
                                                .commentId(1L)
                                                .commentStatus(CommentStatus.SERVICED)
                                                .content("content")
                                                .post(Post.builder()
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
                                                        .build())
                                                .member(Member.builder()
                                                        .memberId(1L)
                                                        .email("a@gmail.com")
                                                        .nickname("nickname")
                                                        .build())
                                                .build()
                                ),
                                PageRequest.of(page, size),
                                1000000
                        ));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/search/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.searchCommentResponses").isArray())
        ;
    }
}
