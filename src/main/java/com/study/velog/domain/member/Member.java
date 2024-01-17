package com.study.velog.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String nickname;

    @Builder
    public Member(String email, String nickname)
    {
        this.email = email;
        this.nickname = nickname;
    }

    public void update(String email, String nickname)
    {
        setEmail(email);
        setNickname(nickname);
    }

    private void setNickname(String nickname)
    {
        if (nickname == null || nickname.isBlank())
        {
            return ;
        }
        this.nickname = nickname;
    }

    private void setEmail(String email) 
    {
        if (email == null || email.isBlank())
        {
            return ;
        }
        this.email = email;
    }
}
