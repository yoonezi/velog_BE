package com.study.velog.api.controller.post;

import com.study.velog.api.controller.post.dto.request.PostSearchCondition;
import com.study.velog.api.controller.post.dto.request.PostSortType;
import com.study.velog.api.controller.post.dto.response.MainPostsResponse;
import com.study.velog.api.controller.post.dto.response.MyPostResponse;
import com.study.velog.api.controller.post.dto.response.PostResponse;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostStatus;
import com.study.velog.domain.type.PostCategory;
import com.study.velog.repository.PostQuerydslRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public PostResponse findPost(@PathVariable Long postId, HttpServletRequest request, HttpServletResponse response)
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

    @GetMapping("/my-posts")
    public MyPostResponse findMyPost(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));
        PageRequest pageable = PageRequest.of(page, size);
        PostSearchCondition condition = new PostSearchCondition(
                null,
                pageable,
                null,
                member.getMemberId()
        );
        return postQuerydslRepository.findMyPosts(condition);
//        Page<Post> posts = postRepository.findMyPosts(member.getEmail(), PageRequest.of(page, size));
//        return MyPostResponse.of(page, posts.getTotalElements(), posts);
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

//    @GetMapping("/main2")
//    public MainPostsResponse findMainPosts2(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "postSortType", defaultValue = "LATEST") PostSortType postSortType,
//            @RequestParam(value = "category", required = false) PostCategory postCategory
//    ) {
//        PageRequest pageable = PageRequest.of(page, size);
//        PostSearchCondition condition = new PostSearchCondition(
//                postCategory,
//                pageable,
//                postSortType
//        );
//        return postQuerydslRepository.findPosts2(condition);
//    }

//    @GetMapping("/main2")
//    public MainPostsResponse findPosts(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "sort", defaultValue = "LATEST") MainSort mainSort
//    ) {
//        return postQuerydslRepository.findPosts(PageRequest.of(page, size, mainSort.getSort()));
//    }
//
//    @GetMapping("/like")
//    public MainPostResponse findLatestPosts(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size
//    ) {
//        Page<Post> posts = postRepository.findLikePosts(PageRequest.of(page, size));
//        return MainPostResponse.of(page, posts);
//    }

//    @GetMapping("/category/{category}")
//    public PostByCategoryResponse findPostByCategory(@PathVariable String category)
//    {
//        List<Post> posts = postRepository.findAll();
//        Map<PostCategory, List<Post>> postsByCategory = Map.of(PostCategory.valueOf(category), posts);
//        return PostByCategoryResponse.of(postsByCategory);
//    }
}
