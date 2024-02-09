package com.study.velog.domain.member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    Member findByMemberStatus(MemberStatus memberStatus);

    @EntityGraph(attributePaths = {"memberRoles"})
    @Query("select m from Member m where m.email = :email")
    Member getWithRoles(@Param("email") String email);
}