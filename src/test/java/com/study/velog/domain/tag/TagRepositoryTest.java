package com.study.velog.domain.tag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    void findExistTags()
    {
        // given
        tagRepository.save(Tag.builder().tagContent("tag1").build());
        tagRepository.save(Tag.builder().tagContent("tag2").build());
        tagRepository.save(Tag.builder().tagContent("tag3").build());

        // when
        List<Tag> tags = tagRepository.findTagsTagContent(List.of("tag1", "tag2", "tag3", "tag4"));

        // then
        assertThat(tags).hasSize(3);
        assertThat(tags).extracting(Tag::getTagContent)
                .contains("tag1", "tag2", "tag3");
    }
}