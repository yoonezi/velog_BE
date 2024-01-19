package com.study.velog.domain.post;

import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
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

}