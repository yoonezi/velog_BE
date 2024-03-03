package com.study.velog.api.controller.postLike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostImage;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.post.PostTag;
import com.study.velog.domain.postLike.LikeStatus;
import com.study.velog.domain.postLike.PostLike;
import com.study.velog.domain.postLike.PostLikeRepository;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.type.PostCategory;
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
import java.util.Set;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostLikeSearchController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class PostLikeSearchControllerTest {

    @MockBean
    PostLikeRepository postLikeRepository;

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
    void findLike() throws Exception{
        // given
        Member member = Member.builder()
                        .memberId(1L)
                        .email("a@gmail.com")
                        .nickname("nickname")
                        .build();

        Post post = Post.builder()
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
                .build();


        BDDMockito.given(AuthUtil.currentUserEmail()).willReturn("test");
        BDDMockito.given(postLikeRepository.findByPostLike(Mockito.any(), Mockito.anyString()))
                .willReturn(
                        Optional.of(PostLike.builder()
                                .postLikeId(1L)
                                .post(post)
                                .member(member)
                                .likeStatus(LikeStatus.LIKE)
                                .build())
                );

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/postLike/search/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.likeStatus").value("LIKE"));
    }
}
