package com.study.velog.domain.postLike;

import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.type.PostCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostLikeRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    void findByPostLike()
    {
        // given
        Member member = memberRepository.save(Member.builder()
                .email("email@gmail.com")
                .nickname("nickname")
                .build());

        Post post =postRepository.save(Post.builder()
                .content("content")
                .title("title")
                .postTags(new HashSet<>())
                .categoryType(PostCategory.AI)
                .member(member)
                .build());

        postLikeRepository.save(PostLike.builder().member(member).post(post).build());

        // when
        Optional<PostLike> postLike = postLikeRepository.findByPostLike(post.getPostId(), member.getMemberId());

        // then
        assertThat(postLike).isNotEmpty();
        assertThat(postLike.get()).extracting(pl -> pl.getMember().getMemberId(), pl ->pl.getPost().getPostId())
                .contains(post.getPostId(), member.getMemberId());
    }


}