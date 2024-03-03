package com.study.velog.api.controller.postImage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.velog.api.service.upload.PostImageUploadResponse;
import com.study.velog.api.service.upload.PostImageUploadService;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.security.filter.JWTCheckFilter;
import com.study.velog.domain.member.Member;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ImageUploadController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTCheckFilter.class)
        })
@WithMockUser
class ImageUploadControllerTest {

    @MockBean
    PostImageUploadService postImageUploadService;

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
    void uploadPostImage() throws Exception {
        // given
        Optional<Member> member = Optional.of(
                Member.builder()
                        .memberId(1L)
                        .email("a@gmail.com")
                        .nickname("nickname")
                        .build()
        );

        String originalFileName = "test.jpg";
        MultipartFile multipartFile = new MockMultipartFile("file", originalFileName, "image/jpeg", new byte[0]);

        BDDMockito.given(AuthUtil.currentUserEmail()).willReturn("test");

        BDDMockito.given(postImageUploadService.uploadPostImage(Mockito.any()))
                .willReturn(
                        PostImageUploadResponse.builder()
                                .originFileName(originalFileName)
                                .url("url")
                                .build()
                );

        // when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/upload/image/post")
                        .file("multipartFile", multipartFile.getBytes())
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originFileName").value("test.jpg"))
                .andExpect(jsonPath("$.url").value("url"));
    }
}