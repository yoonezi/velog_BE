package com.study.velog.api.controller.post;

import com.study.velog.api.controller.post.dto.response.PostResponse;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/post/search")
public class PostSearchController {

    private final PostRepository postRepository;

    @GetMapping("/{postId}")
    public PostResponse findPost(@PathVariable Long postId)
    {
        Post post = postRepository.findPostWithFetch(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        return PostResponse.of(post);
    }
}
