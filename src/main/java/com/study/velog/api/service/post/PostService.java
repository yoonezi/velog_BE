package com.study.velog.api.service.post;

import com.study.velog.api.service.post.dto.request.CreatePostServiceRequest;
import com.study.velog.api.service.post.dto.request.UpdatePostServiceRequest;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.post.PostTag;
import com.study.velog.domain.tag.Tag;
import com.study.velog.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public Long createPost(CreatePostServiceRequest request)
    {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = Post.builder()
                .member(member)
                .content(request.content())
                .title(request.title())
                .build();

        List<PostTag> postTags = createPostTagList(request.tagList());
        postTags.forEach(post::addPostTag);
        return postRepository.save(post).getPostId();
    }

    private List<PostTag> createPostTagList(List<String> tagList)
    {
        List<Tag> findTags = tagRepository.findTagsTagContent(tagList);

        List<String> findTagContents = findTags.stream()
                .map(Tag::getTagContent)
                .collect(Collectors.toList());

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
                                .build()
                        )
                                .collect(Collectors.toList());
    }

    public Long updatePost(UpdatePostServiceRequest request)
    {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        post.update(request.title(), request.content());
        return post.getPostId();
    }

    public void deletePost(Long postId)
    {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}
