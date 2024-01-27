package com.study.velog.api.controller.post;

import com.study.velog.api.controller.post.dto.response.MainPostResponse;
import com.study.velog.api.controller.post.dto.response.PostResponse;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

        if (!PostStatus.SERVICED.equals(post.getPostStatus()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_POST);
        }

        return PostResponse.of(post);
    }

    @GetMapping()
    public MainPostResponse findMainPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Page<Post> posts = postRepository.findMainPosts(PageRequest.of(page, size));
        return MainPostResponse.of(page, posts);
    }
}
