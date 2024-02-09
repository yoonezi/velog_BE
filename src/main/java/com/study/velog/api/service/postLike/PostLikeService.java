package com.study.velog.api.service.postLike;

import com.study.velog.api.service.feed.PostFeedService;
import com.study.velog.api.service.postLike.dto.request.CreatePostLikeServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.postLike.PostLike;
import com.study.velog.domain.postLike.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostFeedService postFeedService;

    public Long like(CreatePostLikeServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        PostLike postLike = PostLike.create(post, member);

        postFeedService.createPostLikePostFeed(post.getPostId(), post.getMember().getMemberId());

        return postLikeRepository.save(postLike).getPostLikeId();
    }

    public void dislike(Long postId)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        postLikeRepository.deleteByMemberAndPost(member, post);
    }
}
