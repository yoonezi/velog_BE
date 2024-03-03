package com.study.velog.api.service.post;

import com.study.velog.api.service.post.dto.PostCreatedEvent;
import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import com.study.velog.api.service.postImage.dto.request.CreatePostImageServiceRequest;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.*;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import com.study.velog.repository.PostQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    //TODO 확인
    private final PostQuerydslRepository postQuerydslRepository;

    public Long createPost(CreatePostServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post;
        if (request.postStatus().equals("SERVICED")) {
            post = Post.create(member, request.content(), request.title(), request.categoryType());
        } else {
            post = Post.pending(member, request.content(), request.title(), request.categoryType());
        }

        List<PostTag> postTags = createPostTagList(request.tagList());
        postTags.forEach(post::addPostTag);

        List<PostImage> postImageList = createPostImageList(request.postImageRequestList());
        postImageList.forEach(post::addPostImage);
        Post postSaved = postRepository.save(post);
        applicationEventPublisher.publishEvent(PostCreatedEvent.of(postSaved.getPostId()));
        return postSaved.getPostId();
    }

    private List<PostImage> createPostImageList(List<CreatePostImageServiceRequest> postImages)
    {
        return postImages.stream()
                .map(request -> PostImage.builder()
                        .url(request.url())
                        .imageOrder(request.order())
                        .build())
                .collect(Collectors.toList());
    }

    private List<PostTag> createPostTagList(List<String> tagList)
    {
        List<Tag> findTags = tagRepository.findTagsTagContent(tagList);

        List<String> findTagContents = findTags.stream()
                .map(Tag::getTagContent)
                .toList();

        tagList.removeAll(findTagContents);

        List<Tag> newTags = tagList.stream()
                .map(tagContent -> Tag.builder()
                        .tagContent(tagContent)
                        .build())
                .collect(Collectors.toList());

        tagRepository.saveAll(newTags);
        findTags.addAll(newTags);

        return findTags.stream()
                .map(tag -> PostTag.builder()
                        .tag(tag)
                        .build())
                .collect(Collectors.toList());
    }

    public Long updatePost(UpdatePostServiceRequest request)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postQuerydslRepository.findMyPostWithFetch(request.postId(), member.getMemberId())
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        if (!post.getMember().getMemberId().equals(member.getMemberId()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_POST);
        }

        List<Tag> newTags = createNewTags(post, request.tagList());
        tagRepository.saveAll(newTags);
        updatePostTag(post, newTags, request.tagList());

        List<PostImage> postImages = updatePostImageList(request);

        post.update(request.title(), request.content(), request.categoryType(), postImages);

        return post.getPostId();
    }

    private static List<PostImage> updatePostImageList(UpdatePostServiceRequest request)
    {
        return request.postImageRequestList().stream()
                .map(postImageRequest -> PostImage.builder()
                        .url(postImageRequest.url())
                        .imageOrder(postImageRequest.order())
                        .build())
                .toList();
    }

    private List<Tag> createNewTags(Post post, List<String> requestTags)
    {
        Set<String> postTagContents = post.getPostTags().stream()
                .map(postTag -> postTag.getTag().getTagContent())
                .collect(Collectors.toSet());

        return requestTags.stream()
                .filter(Predicate.not(postTagContents::contains))
                .map(requestTag -> Tag.builder().tagContent(requestTag).build())
                .collect(Collectors.toList());
    }

    private void updatePostTag(Post post, List<Tag> newTags, List<String> requestTags)
    {
        post.getPostTags().removeIf(postTag -> !requestTags.contains(postTag.getTag().getTagContent()));
        newTags.forEach(tag -> post.addPostTag(PostTag.builder().tag(tag).build()));
    }

    public void deletePost(Long postId)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

        if (!post.getMember().getMemberId().equals(member.getMemberId()))
        {
            throw new ApiException(ErrorCode.INVALID_ACCESS_POST);
        }

        post.delete();
    }
}
