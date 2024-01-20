package com.study.velog.api.service.post;

import com.study.velog.domain.post.PostTag;
import com.study.velog.domain.tag.Tag;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

public class YzTest {
    @Test
    void yz()
    {
        // 기존 post의 tag 가져오기
        List<PostTag> postTags = Lists.newArrayList(
                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag1").build())
                        .build(),

                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag2").build())
                        .build(),

                PostTag.builder()
                        .tag(Tag.builder().tagContent("tag3").build())
                        .build()
        );

        List<String> tags = Lists.newArrayList("tag1", "tag2", "tag4");
        // [1,2,3]   tags: [1,2,4]
        // 기존 tag - 요청에 없는 태그 = 요청에 있는 기본 태그만 남기기
        postTags.removeIf(postTag -> !tags.contains(postTag.getTag().getTagContent()));

        for (PostTag postTag : postTags)
        {
            System.out.println(postTag.getTag().getTagContent());
        }
    }
}
