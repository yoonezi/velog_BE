package com.study.velog.api.controller.post;

import com.study.velog.api.controller.post.dto.request.PostSearchCondition;
import com.study.velog.api.controller.post.dto.request.PostSortType;
import com.study.velog.api.controller.post.dto.response.MainPostsResponse;
import com.study.velog.api.controller.post.dto.response.MyPostResponse;
import com.study.velog.api.controller.post.dto.response.PostResponse;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.type.PostCategory;
import com.study.velog.repository.PostQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/post/search")
@CrossOrigin("*")
public class PostSearchController {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostQuerydslRepository postQuerydslRepository;

    @GetMapping("/{postId}")
    public PostResponse findPost(@PathVariable Long postId)
    {
        Post post = postQuerydslRepository.findPostWithFetch(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        postRepository.increaseViewCount(postId);

        return PostResponse.of(post);
    }

    @GetMapping("/member/{memberId}")
    public MyPostResponse findMyPost(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "postSortType", defaultValue = "LATEST", required = false) PostSortType postSortType
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageable = PageRequest.of(page, size);
        PostSearchCondition condition = new PostSearchCondition(
                null,
                pageable,
                postSortType,
                member.getMemberId()
        );
        return postQuerydslRepository.findMyPosts(condition);
    }

    @GetMapping("/main")
    public MainPostsResponse findMainPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "postSortType", defaultValue = "LATEST") PostSortType postSortType,
            @RequestParam(value = "category", required = false) PostCategory postCategory
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        PostSearchCondition condition = new PostSearchCondition(
                postCategory,
                pageable,
                postSortType,
                null
        );
        return postQuerydslRepository.findPosts(condition);
    }
}
