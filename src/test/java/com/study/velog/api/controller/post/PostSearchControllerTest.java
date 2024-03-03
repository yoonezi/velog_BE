package com.study.velog.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.*;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.type.PostCategory;
import com.study.velog.repository.PostQuerydslRepository;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostSearchController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
})
@WithMockUser
class PostSearchControllerTest {

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    PostRepository postRepository;

    @MockBean
    PostQuerydslRepository postQuerydslRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void searchPost() throws Exception
    {
        // given
        BDDMockito.given(postQuerydslRepository.findPostWithFetch(Mockito.any()))
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
                        .with(csrf())
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

//    @Test
//    void findMemberPost_Success() throws Exception {
//        // given
//        Long memberId = 1L;
//        int page = 0;
//        int size = 10;
//
//        Member member = Member.builder()
//                .memberId(memberId)
//                .email("a@gmail.com")
//                .nickname("nickname")
//                .build();
//
//        BDDMockito.given(memberRepository.findById(memberId))
//                .willReturn(Optional.of(member));
//
//        PageRequest pageable = PageRequest.of(page, size);
//        List<Post> posts = new ArrayList<>();
//        Post post = Post.builder()
//                .postId(1L)
//                .member(member)
//                .postStatus(PostStatus.SERVICED)
//                .content("content")
//                .title("title")
//                .postImageList(Set.of(
//                        PostImage.builder().postImageId(1L).url("url1").imageOrder(1).build(),
//                        PostImage.builder().postImageId(2L).url("url2").imageOrder(2).build()))
//                .categoryType(PostCategory.AI)
//                .postTags(Set.of(PostTag.builder().postTagId(1L).tag(Tag.builder().tagId(1L).tagContent("tag1").build()).build()))
//                .build();
//        posts.add(post);
//        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());
//        BDDMockito.given(postQuerydslRepository.findMyPosts(Mockito.any()))
//                .willReturn(MemberPostsResponse.builder()
//                        .page(0)
//                        .size(4)
//                        .totalElementCount(1)
//                        .postResponses(
//                                Lists.newArrayList(
//                                        PostResponse.of(
//                                                1L,
//                                                "url1",
//                                                "title",
//                                                "nickname",
//                                                LocalDateTime.now(),
//                                                "content",
//                                                0
//                                        )
//                                )
//                        )
//                )
//                .build(););
//
//        // when
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/search/member/{memberId}", memberId)
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                // then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.page").value(page))
//                .andExpect(jsonPath("$.size").value(size))
//                .andExpect(jsonPath("$.totalElementCount").value(1))
//                .andExpect(jsonPath("$.postResponses[0].title").value("title"))
//                .andExpect(jsonPath("$.postResponses[0].content").value("content"))
//                .andExpect(jsonPath("$.postResponses[0].categoryType").value("AI"))
//                .andExpect(jsonPath("$.postResponses[0].tagList[0]").value("tag1"));
//
//    }


}