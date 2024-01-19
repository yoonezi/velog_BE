package com.study.velog.domain.member;

import com.study.velog.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Builder
    public Member(
            Long memberId,
            String email,
            String nickname
    ) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
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
}