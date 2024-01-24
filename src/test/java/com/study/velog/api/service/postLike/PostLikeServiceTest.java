package com.study.velog.api.service.postLike;

import com.study.velog.api.service.postLike.dto.request.CreatePostLikeServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.postLike.LikeStatus;
import com.study.velog.domain.postLike.PostLike;
import com.study.velog.domain.postLike.PostLikeRepository;
import com.study.velog.domain.type.PostCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("포스트 좋아요 서비스 테스트")
@SpringBootTest
@Transactional
class PostLikeServiceTest {

    @Autowired
    PostLikeService postLikeService;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("포스트 좋아요 생성")
    void craatePostLike() {
        // given
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .categoryType(PostCategory.AI)
                .postTags(new ArrayList<>())
                .postStatus(PostStatus.SERVICED)
                .build());

        CreatePostLikeServiceRequest postLike = new CreatePostLikeServiceRequest(
                post.getPostId());

        // when
        Long postLikeId = postLikeService.like(postLike);

        // then
        assertThat(postLikeId).isNotNull();
        List<PostLike> all = postLikeRepository.findAll();
        assertThat(all).hasSize(1);
        PostLike createdPostLike = all.get(0);
        assertThat(createdPostLike.getMember().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(createdPostLike.getPost().getPostId()).isEqualTo(post.getPostId());
    }

    @Test
    @DisplayName("포스트 좋아요 삭제")
    void deletePostLIke()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .categoryType(PostCategory.AI)
                .postTags(new ArrayList<>())
                .postStatus(PostStatus.SERVICED)
                .build());

        postLikeRepository.save(PostLike.builder()
                .post(post)
                .member(member)
                .likeStatus(LikeStatus.LIKE)
                .build());

        // when
        postLikeService.dislike(post.getPostId());
        List<PostLike> postLikes = postLikeRepository.findAll();
        PostLike findPostLike = postLikes.get(0);

        // then
        assertThat(findPostLike.getLikeStatus()).isEqualTo(LikeStatus.UNLIKE);
    }

}