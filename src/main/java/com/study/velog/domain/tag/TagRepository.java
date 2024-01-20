package com.study.velog.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.tagContent IN (:tagNames)")
    List<Tag> findTagsTagContent(@Param("tagNames") List<String> tagNames);

    Optional<Tag> findByTagContent(String tagContent);
}
