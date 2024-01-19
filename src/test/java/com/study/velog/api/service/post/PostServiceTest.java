package com.study.velog.api.service.post;

import com.google.common.collect.Lists;
import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.tag.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostService postService;

    @Autowired
    TagRepository tagRepository;

    @Test
    @DisplayName("포스트 생성")
    void createPost()
    {
        // given
        Member member = memberRepository.save(Member.builder()
                .email("a@gmail.com")
                .nickname("a")
                .build());

        CreatePostServiceRequest request = CreatePostServiceRequest.builder()
                .memberId(member.getMemberId())
                .title("title")
                .content("content")
                .tagList(Lists.newArrayList("tag1", "tag2"))
                .build();

        // when
        Long postId = postService.createPost(request);
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);

        // then
        assertThat(posts).hasSize(1);
        assertThat(postId).isEqualTo(post.getPostId());
        assertThat(post.getPostTags()).hasSize(2);
    }

    @Test
    @DisplayName("포스트 제목 수정")
    void updatePost()
    {
        // given
        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .build());

        UpdatePostServiceRequest request = UpdatePostServiceRequest.builder()
                .postId(post.getPostId())
                .title("updatedTitle")
                .content(post.getContent())
                .build();

        // when
        Long postId = postService.updatePost(request);
        List<Post> posts = postRepository.findAll();
        Post findPost = posts.get(0);

        // then
        assertThat(post).extracting(s->s.getTitle(), s->s.getContent())
                        .contains(request.title(), "content");
    }

    @Test
    @DisplayName("포스트 삭제")
    void deletePost()
    {
        // given
        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .build());

        // when
        postService.deletePost(post.getPostId());
        List<Post> posts = postRepository.findAll();

        // then
        assertThat(posts).isEmpty();
    }

}