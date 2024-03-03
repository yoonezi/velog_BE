package com.study.velog.api.controller.post;

import com.study.velog.api.controller.post.dto.request.PostSearchCondition;
import com.study.velog.api.controller.post.dto.request.PostSortType;
import com.study.velog.api.controller.post.dto.response.PostResponse;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.api.controller.post.dto.response.MyPendingPostsResponse;
import com.study.velog.repository.PostQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/post/pending")
public class PostMemberController {

    private final MemberRepository memberRepository;
    private final PostQuerydslRepository postQuerydslRepository;

    @GetMapping("/posts")
    public MyPendingPostsResponse findPendingPost(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "postSortType", defaultValue = "LATEST", required = false) PostSortType postSortType
    ) {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageable = PageRequest.of(page, size);
        PostSearchCondition condition = new PostSearchCondition(
                null,
                pageable,
                postSortType,
                member.getMemberId()
        );
        return postQuerydslRepository.findMyPendingPosts(condition);
    }

    @GetMapping("/{postId}")
    public PostResponse findPendingPost(@PathVariable Long postId)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postQuerydslRepository.findMyPendingPostWithFetch(postId, member.getMemberId())
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        return PostResponse.of(post);
    }

}
