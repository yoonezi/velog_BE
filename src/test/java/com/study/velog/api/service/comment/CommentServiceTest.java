//package com.study.velog.api.service.comment;
//
//import com.study.velog.api.service.comment.dto.request.CreateCommentServiceRequest;
//import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
//import com.study.velog.domain.comment.Comment;
//import com.study.velog.domain.comment.CommentRepository;
//import com.study.velog.domain.member.Member;
//import com.study.velog.domain.member.MemberRepository;
//import com.study.velog.domain.member.MemberStatus;
//import com.study.velog.domain.post.Post;
//import com.study.velog.domain.post.PostRepository;
//import com.study.velog.domain.post.PostStatus;
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
//@DisplayName("댓글 서비스 테스트")
//@SpringBootTest
//@Transactional
//class CommentServiceTest {
//
//    @Autowired
//    CommentService commentService;
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Test
//    @DisplayName("댓글 생성")
//    void createComment()
//    {
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
//        CreateCommentServiceRequest comment = new CreateCommentServiceRequest(
//                "content",
//                post.getPostId(),
//                member.getMemberId())
//                ;
//
//        // when
//        Long commentId = commentService.createComment(comment);
//
//        // then
//        assertThat(commentId).isNotNull();
//        List<Comment> all = commentRepository.findAll();
//        assertThat(all).hasSize(1);
//        Comment createdComment = all.get(0);
//        assertThat(createdComment.getMember().getMemberId()).isEqualTo(member.getMemberId());
//        assertThat(createdComment.getPost().getPostId()).isEqualTo(post.getPostId());
//    }
//
//    @Test
//    @DisplayName("댓글 수정")
//    void updateComment()
//    {
//        // given
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
//        Comment comment = commentRepository.save(Comment.builder()
//                .content("content")
//                .member(member)
//                .post(post)
//                .build());
//
//
//        UpdateCommentServiceRequest request = new UpdateCommentServiceRequest(
//                comment.getCommentId(),
//                "updateContent",
//                comment.getPost().getPostId(),
//                comment.getMember().getMemberId());
//
//        // when
//        Long commentId = commentService.updateComment(request);
//        List<Comment> comments = commentRepository.findAll();
//        Comment findComment = comments.get(0);
//
//        // then
//        assertThat(findComment.getContent()).isEqualTo("updateContent");
//    }
//
//    @Test
//    @DisplayName("댓글 삭제")
//    void deleteComment()
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
//        Comment comment = commentRepository.save(Comment.builder()
//                .content("content")
//                .member(member)
//                .post(post)
//                .build());
//
//        // when
//        commentService.deleteComment(comment.getCommentId());
//        List<Comment> comments = commentRepository.findAll();
//
//        // then
//        assertThat(comments).isEmpty();
//    }
//}