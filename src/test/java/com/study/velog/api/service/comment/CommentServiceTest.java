package com.study.velog.api.service.comment;

import com.study.velog.IntegrationTestSupport;
import com.study.velog.api.service.comment.dto.request.CreateCommentServiceRequest;
import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.comment.Comment;
import com.study.velog.domain.comment.CommentRepository;
import com.study.velog.domain.comment.CommentStatus;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.type.PostCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("댓글 서비스 테스트")
@Transactional
class CommentServiceTest extends IntegrationTestSupport {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("댓글 생성")
    void createComment()
    {
        // given
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .member(member)
                .categoryType(PostCategory.AI)
                .postTags(new HashSet<>())
                .postStatus(PostStatus.SERVICED)
                .build());

        CreateCommentServiceRequest comment = new CreateCommentServiceRequest(
                "content",
                post.getPostId())
                ;

        // when
        Long commentId = commentService.createComment(comment);

        // then
        assertThat(commentId).isNotNull();
        List<Comment> all = commentRepository.findAll();
        assertThat(all).hasSize(1);
        Comment createdComment = all.get(0);
        assertThat(createdComment.getMember().getEmail()).isEqualTo(AuthUtil.currentUserEmail());
        assertThat(createdComment.getPost().getPostId()).isEqualTo(post.getPostId());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment()
    {
        // given
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .categoryType(PostCategory.AI)
                .postTags(new HashSet<>())
                .build());

        Comment comment = commentRepository.save(Comment.builder()
                .content("content")
                .member(member)
                .post(post)
                .build());


        UpdateCommentServiceRequest request = new UpdateCommentServiceRequest(
                comment.getCommentId(),
                "updateContent",
                comment.getPost().getPostId());

        // when
        Long commentId = commentService.updateComment(request);
        List<Comment> comments = commentRepository.findAll();
        Comment findComment = comments.get(0);

        // then
        assertThat(findComment.getContent()).isEqualTo("updateContent");
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment()
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .categoryType(PostCategory.AI)
                .postTags(new HashSet<>())
                .build());

        Comment comment = commentRepository.save(Comment.builder()
                .content("content")
                .member(member)
                .post(post)
                .commentStatus(CommentStatus.SERVICED)
                .build());

        // when
        commentService.deleteComment(comment.getCommentId());
        List<Comment> comments = commentRepository.findAll();
        Comment findComment = comments.get(0);

        // then
        assertThat(findComment.getCommentStatus()).isEqualTo(CommentStatus.DELETED);
    }
}