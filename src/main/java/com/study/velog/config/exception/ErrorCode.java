package com.study.velog.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    //Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 없습니다."),
    MEMBER_EMAIL_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일이 중복되었습니다."),
    MEMBER_STATUS_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, "멈버가 삭제된 상태입니다."),

    //Follow
    SELF_FOLLOW(HttpStatus.INTERNAL_SERVER_ERROR, "이메일이 중복되었습니다."),
    ALREADY_FOLLOW(HttpStatus.INTERNAL_SERVER_ERROR, "이미 팔로우 하고 있는 유저"),
    NOT_FOUND_FOLLOW(HttpStatus.NOT_FOUND, "팔로우 유저 아님"),


    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "포스트가 없습니다."),
    POST_STATUS_DELETED(HttpStatus.NOT_FOUND, "포스트가 삭제된 상태입니다."),
    POST_STATUS_PENDING(HttpStatus.INTERNAL_SERVER_ERROR, "포스트가 임시 저장된 상태입니다."),
    INVALID_ACCESS_POST(HttpStatus.FORBIDDEN, "잘못된 접근입니다."),

    //PostLike
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요가 없습니다."),
    LIKE_STATUS_UNLIKED(HttpStatus.INTERNAL_SERVER_ERROR, "좋아요가 취소된 상태입니다."),


    //Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 없습니다."),
    COMMENT_STATUS_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, "댓글이 삭제된 상태입니다."),
    INVALID_ACCESS_COMMENT(HttpStatus.FORBIDDEN, "잘못된 접근입니다."),

    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage)
    {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
