package com.study.velog.api.controller.comment;

import com.study.velog.domain.comment.Comment;
import com.study.velog.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/search")
public class CommentSearchController {

    private final CommentRepository commentRepository;

    @GetMapping("/{postId}")
    public void findComments(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("commentId").descending());
        Page<Comment> comments = commentRepository.findByPostIdWithPage(postId, pageRequest);
        // todo convert..
    }
}
