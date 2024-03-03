package com.study.velog.api.controller.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.service.feed.*;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import org.assertj.core.util.Lists;
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

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FeedController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class FeedControllerTest {

    @MockBean
    PostFeedService postFeedService;

    @MockBean
    FollowFeedService followFeedService;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void getMemberPostFeeds() throws Exception {

        // given
        BDDMockito.given(followFeedService.findMemberFollowFeeds(Mockito.any()))
                .willReturn(
                        Lists.newArrayList(
                                FollowFeed.builder()
                                        .memberEmail("a@gmail.com")
                                        .memberId(1L)
                                        .task(FeedTaskType.ADD_FOLLOW)
                                        .localDateTime(LocalDateTime.now())
                                        .build()
                        )
                );

        BDDMockito.given(postFeedService.findMemberPostFeeds(Mockito.any()))
                .willReturn(
                        Lists.newArrayList(
                                PostFeed.builder()
                                        .postId(1L)
                                        .memberEmail("a@gmail.com")
                                        .memberId(2L)
                                        .task(FeedTaskType.ADD_COMMENT)
                                        .localDateTime(LocalDateTime.now())
                                        .build()
                        )
                );

        BDDMockito.given(memberRepository.findAllById(Mockito.any()))
                .willReturn(Lists.newArrayList(
                        Member.builder()
                                .email("a@gmail.com")
                                .nickname("a")
                                .memberId(1L)
                                .build(),
                        Member.builder()
                                .email("yz2@kakao.com")
                                .nickname("yz2")
                                .memberId(2L)
                                .build()
                ));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/feed")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userFeeds[0].feedTaskType").value(FeedTaskType.POST_LIKE)) // Correct task type for FollowFeed
                .andExpect(jsonPath("$.userFeeds[0].message").value("a 님이 회원님을 팔로우 시작했습니다.")) // Correct message for FollowFeed
        ;
    }
}