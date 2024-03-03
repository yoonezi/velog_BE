package com.study.velog.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.repository.PostQuerydslRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PostMemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class PostStatusControllerTest {
    @MockBean
    MemberRepository memberRepository;

    @MockBean
    PostQuerydslRepository postQuerydslRepository;

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

//    @Test
//    void findPendingPost() throws Exception {
//
//        Member member = Member.builder()
//                .memberId(1L)
//                .email("a@gmail.com")
//                .nickname("nickname")
//                .memberRoles(List.of(MemberRole.ADMIN))
//                .build();
//
//        BDDMockito.given(AuthUtil.currentUserEmail()).willReturn("test");
//
//        BDDMockito.given(memberRepository.findByEmail(Mockito.any()))
//                .willReturn(Optional.of(member));
//
//        BDDMockito.given(postQuerydslRepository.findMyPendingPosts(Mockito.any(), Mockito.any()))
//                .willReturn(Optional.of(Follow.builder()
//                        .followerId(1L)
//                        .followeeId(2L)
//                        .build())
//                );


//    }
//
}