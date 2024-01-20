package com.study.velog.api.service.comment;

import com.study.velog.api.service.comment.dto.request.CreateCommentServiceRequest;
import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.comment.Comment;
import com.study.velog.domain.comment.CommentRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Long createComment(CreateCommentServiceRequest request)
    {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.content())
                .member(member)
                .post(post)
                .build();

        commentRepository.save(comment);
        return comment.getCommentId();
    }

    public Long updateComment(UpdateCommentServiceRequest request)
    {
        Comment comment = commentRepository.findById(request.commentId())
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));
        comment.update(request.content());
        return comment.getCommentId();
    }

    public void deleteComment(Long commentId)
    {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
