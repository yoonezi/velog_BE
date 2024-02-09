package com.study.velog.domain.post;

import com.study.velog.api.controller.post.dto.response.MainPostLikeCountOrder;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findPostWithFetch()
    {
        // given
        Member member = memberRepository.save(Member.builder().email("email@gmail.com").nickname("nickname").build());

        Tag tag1 = tagRepository.save(Tag.builder().tagContent("tag1").build());
        Tag tag2 = tagRepository.save(Tag.builder().tagContent("tag2").build());
        Tag tag3 = tagRepository.save(Tag.builder().tagContent("tag3").build());
        PostTag postTag1 = PostTag.builder().tag(tag1).build();
        PostTag postTag2 = PostTag.builder().tag(tag2).build();
        PostTag postTag3 = PostTag.builder().tag(tag3).build();

        Post post = Post.builder()
                .content("content")
                .title("title")
                .member(member)
                .build();
        post.addPostTag(postTag1);
        post.addPostTag(postTag2);
        post.addPostTag(postTag3);

        postRepository.save(post);

        Optional<Post> postWithFetch = postRepository.findPostWithFetch(post.getPostId());

        System.out.println(postWithFetch.get());
  }

  @Test
  void findMainPosts()
  {
      // given
      Member member = memberRepository.save(Member.builder().email("email@gmail.com").nickname("nickname").build());

      List<PostImage> postsImages1 = List.of(
              PostImage.builder().imageOrder(1).url("url1").build(),
              PostImage.builder().imageOrder(2).url("url2").build()
      );

      Post post1 = Post.builder()
              .content("content")
              .title("title")
              .member(member)
              .build();

      postsImages1.forEach(postImage -> post1.addPostImage(postImage));

      List<PostImage> postsImages2 = List.of(
              PostImage.builder().imageOrder(1).url("url3").build(),
              PostImage.builder().imageOrder(2).url("url4").build()
      );

      Post post2 = Post.builder()
              .content("content")
              .title("title")
              .member(member)
              .build();

      postsImages2.forEach(postImage -> post2.addPostImage(postImage));
      postRepository.save(post1);
      postRepository.save(post2);

      // when
      Page<Post> mainPosts = postRepository.findTrendPosts(PageRequest.of(0, 3));

      List<Post> posts = mainPosts.getContent();
      for (Post post : posts)
      {
          System.out.println(post.getPostId() + " ,  " + post.getPostImageList());
      }
  }

  @Test
  void dddd()
  {
      Sort s1 = Sort.by("vct").descending();
      Sort s2 = Sort.by("ct").descending();
      Sort s3 = Sort.by("vct").descending();
      Page<MainPostLikeCountOrder> r = postRepository.readAllBy(PageRequest.of(0, 10, Sort.by("vct").descending()));
      System.out.println(r.getContent());
  }
}