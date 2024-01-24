package com.study.velog.api.service.comment;

import com.study.velog.api.service.comment.dto.request.CreateCommentServiceRequest;
import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.comment.Comment;
import com.study.velog.domain.comment.CommentRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
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
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        if (!PostStatus.SERVICED.equals(post.getPostStatus()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_POST);
        }

        Comment comment = Comment.create(request.content(), post, member);
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    public Long updateComment(UpdateCommentServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Comment comment = commentRepository.findById(request.commentId())
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getMember().getMemberId().equals(member.getMemberId()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_COMMENT);
        }

        comment.update(request.content());
        return comment.getCommentId();
    }

    public void deleteComment(Long commentId)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getMember().getMemberId().equals(member.getMemberId()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_COMMENT);
        }

        comment.delete();
    }
}
