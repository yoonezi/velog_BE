package com.study.velog.api.controller.post;

import com.study.velog.api.controller.post.dto.response.*;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.type.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/post/search")
@CrossOrigin("*")
public class PostSearchController {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/{postId}")
    public PostResponse findPost(@PathVariable Long postId)
    {
        Post post = postRepository.findPostWithFetch(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        if (!PostStatus.SERVICED.equals(post.getPostStatus()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_POST);
        }

        postRepository.increaseViewCount(postId);
        return PostResponse.of(post);
    }

    @GetMapping("/posts/{memberEmail}")
    public MyPostResponse findMyPost(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Page<Post> posts = postRepository.findMyPosts(member.getEmail(), PageRequest.of(page, size));
        return MyPostResponse.of(page, posts.getTotalElements(), posts);
    }

    @AllArgsConstructor
    @Getter
    public enum MainSort {
        LATEST(Sort.by("registerDate").descending()),
        VIEWS(Sort.by("viewCount").descending()),
        LIKES(Sort.by(
                Sort.Order.desc("ct"),
                Sort.Order.desc("registerDate")
        )),
        ;
        private final Sort sort;
    }

    @GetMapping("/main")
    public MainPostResponse findMainPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", defaultValue = "LATEST") MainSort mainSort
    ) {
        if (MainSort.LIKES.equals(mainSort))
        {
            List<Long> postIds = postRepository.readAllBy(PageRequest.of(page, size, mainSort.getSort())).getContent()
                    .stream()
                    .map(MainPostLikeCountOrder::getPostId)
                    .collect(Collectors.toList());

            return MainPostResponse.of(page, postRepository.findAllPostIds(postIds,  PageRequest.of(page, size)));
        }

        return MainPostResponse.of(page, postRepository.findTrendPosts(PageRequest.of(page, size, mainSort.getSort())));
    }

    @GetMapping("/like")
    public MainPostResponse findLatestPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Page<Post> posts = postRepository.findLikePosts(PageRequest.of(page, size));
        return MainPostResponse.of(page, posts);
    }

    @GetMapping("/category/{category}")
    public PostByCategoryResponse findPostByCategory(@PathVariable String category)
    {
        List<Post> posts = postRepository.findAll();
        Map<PostCategory, List<Post>> postsByCategory = Map.of(PostCategory.valueOf(category), posts);
        return PostByCategoryResponse.of(postsByCategory);
    }
}
