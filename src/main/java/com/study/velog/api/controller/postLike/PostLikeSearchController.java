package com.study.velog.api.controller.postLike;

import com.study.velog.api.controller.postLike.dto.response.SearchPostLikeResponse;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.postLike.LikeStatus;
import com.study.velog.domain.postLike.PostLike;
import com.study.velog.domain.postLike.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/postLike/search")
@CrossOrigin("*")
public class PostLikeSearchController {

    private final PostLikeRepository postLikeRepository;

    @GetMapping("/{postId}")
    public SearchPostLikeResponse findLike(@PathVariable Long postId) {
        Optional<PostLike> postLike = postLikeRepository.findByPostLike(postId, AuthUtil.currentUserEmail());

        if (postLike.isPresent() && postLike.get().getLikeStatus() == LikeStatus.LIKE)
        {
            return SearchPostLikeResponse.of(postLike.get());
        } else {
            throw new ApiException(ErrorCode.LIKE_STATUS_UNLIKED);
        }
    }
}
