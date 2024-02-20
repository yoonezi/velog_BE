package com.study.velog.repository;

import com.study.velog.IntegrationTestSupport;
import com.study.velog.api.controller.post.dto.request.PostSearchCondition;
import com.study.velog.api.controller.post.dto.request.PostSortType;
import com.study.velog.api.controller.post.dto.response.PostResponses;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.post.PostTag;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import com.study.velog.domain.type.PostCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PostQuerydslRepositoryTest extends IntegrationTestSupport {

    @Autowired
    PostQuerydslRepository postQuerydslRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findPostWithFetch()
    {
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = memberRepository.save(Member.join("test@gmail.com", "test", "test", passwordEncoder));

        Tag tag1 = tagRepository.save(Tag.builder().tagContent("tag1").build());
        Tag tag2 = tagRepository.save(Tag.builder().tagContent("tag2").build());

        Post post = Post.builder()
                .content("content")
                .title("title")
                .member(member)
                .postStatus(PostStatus.SERVICED)
                .categoryType(PostCategory.AI)
                .build();

        post.addPostTag(PostTag.builder().tag(tag1).build());
        post.addPostTag(PostTag.builder().tag(tag2).build());

        postRepository.save(post);

        //when
        Optional<Post> postWithFetch = postQuerydslRepository.findPostWithFetch(post.getPostId());

        //then
        assertThat(postWithFetch.get()).extracting(Post::getContent, Post::getTitle, Post::getCategoryType)
                .contains(post.getContent(), post.getTitle(), post.getCategoryType());
    }

    @Test
    void findAllPosts()
    {
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = memberRepository.save(Member.join("test@gmail.com", "test", "test", passwordEncoder));

        Tag tag1 = tagRepository.save(Tag.builder().tagContent("tag1").build());
        Tag tag2 = tagRepository.save(Tag.builder().tagContent("tag2").build());
        Tag tag3 = tagRepository.save(Tag.builder().tagContent("tag3").build());

        Post post = Post.builder()
                .title("post 1")
                .content("content 1")
                .member(member)
                .postStatus(PostStatus.SERVICED)
                .categoryType(PostCategory.AI)
                .build();

        post.addPostTag(PostTag.builder().tag(tag1).build());
        post.addPostTag(PostTag.builder().tag(tag2).build());

        postRepository.save(post);

        Post post2 = Post.builder()
                .title("post 2")
                .content("content 2")
                .member(member)
                .postStatus(PostStatus.SERVICED)
                .categoryType(PostCategory.GAME)
                .build();

        post2.addPostTag(PostTag.builder().tag(tag2).build());
        post2.addPostTag(PostTag.builder().tag(tag3).build());

        postRepository.save(post2);

        // when
        PostSearchCondition condition = new PostSearchCondition(null,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "registerDate")),
                PostSortType.LATEST, member.getMemberId());
        Page<PostResponses> page = postQuerydslRepository.findAllPosts(condition);

        // Then
        assertThat(page).hasSize(2);
        assertThat(page.getContent().get(0))
                .extracting(PostResponses::getTitle, PostResponses::getContent, PostResponses::getPostCategory,
                        PostResponses::getMemberId, PostResponses::getMemberName)
                .containsExactly("post 1", "content 1", PostCategory.AI, member.getMemberId(), member.getNickname());
    }

    @Test
    void findCategoryPosts()
    {
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = memberRepository.save(Member.join("test@gmail.com", "test", "test", passwordEncoder));

        Tag tag1 = tagRepository.save(Tag.builder().tagContent("tag1").build());
        Tag tag2 = tagRepository.save(Tag.builder().tagContent("tag2").build());
        Tag tag3 = tagRepository.save(Tag.builder().tagContent("tag3").build());

        Post post = Post.builder()
                .title("post 1")
                .content("content 1")
                .member(member)
                .postStatus(PostStatus.SERVICED)
                .categoryType(PostCategory.AI)
                .build();

        post.addPostTag(PostTag.builder().tag(tag1).build());
        post.addPostTag(PostTag.builder().tag(tag2).build());

        postRepository.save(post);

        Post post2 = Post.builder()
                .title("post 2")
                .content("content 2")
                .member(member)
                .postStatus(PostStatus.SERVICED)
                .categoryType(PostCategory.GAME)
                .build();

        post2.addPostTag(PostTag.builder().tag(tag2).build());
        post2.addPostTag(PostTag.builder().tag(tag3).build());

        postRepository.save(post2);

        // when
        PostSearchCondition condition = new PostSearchCondition(PostCategory.AI,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "registerDate")),
                PostSortType.LATEST, member.getMemberId());
        Page<PostResponses> page = postQuerydslRepository.findAllPosts(condition);

        // Then
        assertThat(page).hasSize(1);
        assertThat(page.getContent().get(0))
                .extracting(PostResponses::getTitle, PostResponses::getContent, PostResponses::getPostCategory,
                        PostResponses::getMemberId, PostResponses::getMemberName)
                .containsExactly("post 1", "content 1", PostCategory.AI, member.getMemberId(), member.getNickname());
    }
}