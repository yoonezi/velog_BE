package com.study.velog.api.controller.post;


import com.study.velog.api.controller.post.dto.request.CreatePostRequest;
import com.study.velog.api.controller.post.dto.request.UpdatePostRequest;
import com.study.velog.api.service.post.PostService;
import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long createPost(@Valid @RequestBody CreatePostRequest request)
    {
        CreatePostServiceRequest serviceDto = request.toServiceDto();
        return postService.createPost(serviceDto);
    }

    @PutMapping("/{postId}")
    public Long updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request)
    {
        UpdatePostServiceRequest serviceDto = request.toServiceDto(postId);
        return postService.updatePost(serviceDto);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId)
    {
        postService.deletePost(postId);
    }
}
