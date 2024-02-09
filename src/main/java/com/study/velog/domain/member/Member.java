package com.study.velog.domain.member;

import com.google.common.collect.Lists;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_roles_mapping", joinColumns = @JoinColumn(name = "member_id"))
    private List<MemberRole> memberRoles = new ArrayList<>();

    public boolean matchPassword(String pw, PasswordEncoder passwordEncoder)
    {
        return passwordEncoder.matches(pw, this.password);
    }

    @Builder
    public Member(
            Long memberId,
            String email,
            String nickname,
            String password,
            MemberStatus memberStatus,
            List<MemberRole> memberRoles
    ) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.memberStatus = memberStatus;
        this.memberRoles = memberRoles;
    }

    public static Member join(
            String email,
            String nickname,
            String password,
            PasswordEncoder passwordEncoder
    ) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .memberStatus(MemberStatus.SERVICED)
                .password(passwordEncoder.encode(password))
                .memberRoles(Lists.newArrayList(MemberRole.ADMIN))
                .build();
    }

    public static Member create(
            String email,
            String nickname
    ) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .memberStatus(MemberStatus.SERVICED)
                .build();
    }

    public void update(String email, String nickname)
    {
        setEmail(email);
        setNickname(nickname);
    }

    private void setEmail(String email)
    {
        if (email == null || email.isBlank()) {
            return;
        }
        this.email = email;
    }

    private void setNickname(String nickname)
    {
        if (nickname == null || nickname.isBlank()) {
            return;
        }
        this.nickname = nickname;
    }

    public void delete()
    {
        if (memberStatus.equals(MemberStatus.DELETED))
        {
            throw new ApiException(ErrorCode.POST_STATUS_DELETED);
        }

        this.memberStatus =MemberStatus.DELETED;
    }
}