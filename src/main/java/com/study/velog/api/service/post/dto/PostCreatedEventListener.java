package com.study.velog.api.service.post.dto;

import com.study.velog.domain.post.Post;
import com.study.velog.domain.post.PostRepository;
import com.study.velog.domain.postSearch.PostSearch;
import com.study.velog.domain.postSearch.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostCreatedEventListener {

    private final PostRepository postRepository;
    private final PostSearchRepository postSearchRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handle(PostCreatedEvent postCreatedEvent)
    {
        Post post = postRepository.findById(postCreatedEvent.getPostId()).orElseThrow();

        postSearchRepository.save(PostSearch.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .memberName(post.getMember().getNickname())
                .build());
    }
}
