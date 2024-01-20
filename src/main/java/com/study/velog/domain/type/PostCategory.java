package com.study.velog.domain.type;

import lombok.Getter;

@Getter
public enum PostCategory {
    AI("인공지능"),
    BACKEND("백엔드"),
    DEVOPS("데브옵스"),
    EMBEDDED("임베디드"),
    FRONTEND("프론트엔드"),
    GAME("게임"),
    ;
    private final String content;

    PostCategory(String content)
    {
        this.content = content;
    }
}
