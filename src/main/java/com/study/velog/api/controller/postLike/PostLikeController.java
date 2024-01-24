package com.study.velog.api.controller.postLike;

import com.study.velog.api.controller.postLike.dto.request.CreatePostLikeRequest;
import com.study.velog.api.service.postLike.PostLikeService;
import com.study.velog.api.service.postLike.dto.request.CreatePostLikeServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postLike")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public Long like(@PathVariable Long postId, @Valid @RequestBody CreatePostLikeRequest request)
    {
        CreatePostLikeServiceRequest serviceDto = request.toServiceDto(postId);
        return postLikeService.like(serviceDto);
    }

    @DeleteMapping("/{postId}")
    public void disLike(@PathVariable Long postId)
    {
        postLikeService.dislike(postId);
    }
}
