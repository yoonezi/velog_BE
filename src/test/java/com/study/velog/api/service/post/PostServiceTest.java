package com.study.velog.api.service.post;

import com.google.common.collect.Lists;
import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import com.study.velog.api.service.postImage.dto.request.UpdatePostImageServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostImage;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.tag.TagRepository;
import com.study.velog.domain.type.PostCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        CreatePostServiceRequest request = CreatePostServiceRequest.builder()
                .title("title")
                .content("content")
                .tagList(Lists.newArrayList("tag1", "tag2"))
                .categoryType(PostCategory.AI)
                .postImageRequestList(new ArrayList<>())
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
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .categoryType(PostCategory.AI)
                .postTags(new HashSet<>())
                .postStatus(PostStatus.SERVICED)
                .postImageList(Set.of(
                            PostImage.builder().url("a1").imageOrder(1).build()
                        )
                )
                .build());

        UpdatePostServiceRequest request = UpdatePostServiceRequest.builder()
                .postId(post.getPostId())
                .title("updatedTitle")
                .content(post.getContent())
                .tagList(Lists.newArrayList("tag1", "tag2"))
                .categoryType(post.getCategoryType())
                .postImageRequestList(List.of(UpdatePostImageServiceRequest.builder().url("a1").order(1).build()))
                .build();

        // when
        postService.updatePost(request);
        List<Post> posts = postRepository.findAll();
        Post findPost = posts.get(0);

        // then
        assertThat(findPost).extracting(Post::getTitle, Post::getContent)
                        .contains(request.title(), "content");
    }

    @Test
    @DisplayName("포스트 삭제")
    void deletePost()
    {
        // given
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .categoryType(PostCategory.AI)
                .postTags(new HashSet<>())
                .postStatus(PostStatus.SERVICED)
                .build());

        // when
        postService.deletePost(post.getPostId());
        List<Post> posts = postRepository.findAll();
        Post findPost = posts.get(0);

        // then
        assertThat(findPost.getPostStatus()).isEqualTo(PostStatus.DELETED);
    }

}