package com.study.velog.domain.comment;

import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostTag;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void create()
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

        for (int i = 0; i < 10; i++)
        {
            Comment comment = Comment.builder()
                    .post(post)
                    .member(member)
                    .content("content " + i)
                    .build();

            commentRepository.save(comment);
        }
        PageRequest givenPageRequest = PageRequest.of(0, 3);
        Page<Comment> pageByPost = commentRepository.findByPostIdWithPage(post.getPostId(), givenPageRequest);
        Assertions.assertThat(pageByPost.getContent())
                .hasSize(3);
    }
}