package com.study.velog.api.controller.comment;

import com.study.velog.api.controller.comment.dto.request.CreateCommentRequest;
import com.study.velog.api.controller.comment.dto.request.UpdateCommentRequest;
import com.study.velog.api.service.comment.CommentService;
import com.study.velog.api.service.comment.dto.request.CreateCommentServiceRequest;
import com.study.velog.api.service.comment.dto.request.UpdateCommentServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Long createComment(@Valid @RequestBody CreateCommentRequest request)
    {
        CreateCommentServiceRequest serviceDto = request.toServiceDto();
        return commentService.createComment(serviceDto);
    }

    @PutMapping("/{commentId}")
    public Long updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request)
    {
        UpdateCommentServiceRequest serviceDto = request.toServiceDto(commentId);
        return commentService.updateComment(serviceDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId)
    {
        commentService.deleteComment(commentId);
    }
}
