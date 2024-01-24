//package com.study.velog.api.service.postLike;
//
//import com.study.velog.api.service.postLike.dto.request.CreatePostLikeServiceRequest;
//import com.study.velog.domain.member.Member;
//import com.study.velog.domain.member.MemberRepository;
//import com.study.velog.domain.member.MemberStatus;
//import com.study.velog.domain.post.Post;
//import com.study.velog.domain.post.PostRepository;
//import com.study.velog.domain.post.PostStatus;
//import com.study.velog.domain.postLike.PostLike;
//import com.study.velog.domain.postLike.PostLikeRepository;
//import com.study.velog.domain.type.PostCategory;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("포스트 좋아요 서비스 테스트")
//@SpringBootTest
//@Transactional
//class PostLikeServiceTest {
//
//    @Autowired
//    PostLikeService postLikeService;
//
//    @Autowired
//    PostLikeRepository postLikeRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Test
//    @DisplayName("포스트 좋아요 생성")
//    void craatePostLike() {
//        // given
//        Post post = postRepository.save(Post.builder()
//                .title("title")
//                .content("content")
//                .categoryType(PostCategory.AI)
//                .postTags(new ArrayList<>())
//                .postStatus(PostStatus.SERVICED)
//                .build());
//
//        Member member = memberRepository.save(Member.builder()
//                .email("a@gmail.com")
//                .nickname("a")
//                .memberStatus(MemberStatus.SERVICED)
//                .build());
//
//        CreatePostLikeServiceRequest postLike = new CreatePostLikeServiceRequest(
//                post.getPostId(),
//                member.getMemberId());
//
//        // when
//        Long postLikeId = postLikeService.like(postLike);
//
//        // then
//        assertThat(postLikeId).isNotNull();
//        List<PostLike> all = postLikeRepository.findAll();
//        assertThat(all).hasSize(1);
//        PostLike createdPostLike = all.get(0);
//        assertThat(createdPostLike.getMember().getMemberId()).isEqualTo(member.getMemberId());
//        assertThat(createdPostLike.getPost().getPostId()).isEqualTo(post.getPostId());
//    }
//
//    @Test
//    @DisplayName("포스트 좋아요 삭제")
//    void deletePostLIke()
//    {
//        Post post = postRepository.save(Post.builder()
//                .title("title")
//                .content("content")
//                .categoryType(PostCategory.AI)
//                .postTags(new ArrayList<>())
//                .build());
//
//        Member member =  memberRepository.save(Member.builder()
//                .email("a@gmail.com")
//                .nickname("a")
//                .build());
//
//        PostLike postLike = postLikeRepository.save(PostLike.builder()
//                .post(post)
//                .member(member)
//                .build());
//
//        // when
//        postLikeService.dislike(member.getMemberId(), post.getPostId());
//        List<PostLike> postLikes = postLikeRepository.findAll();
//
//        // then
//        assertThat(postLikes).isEmpty();
//    }
//
//}