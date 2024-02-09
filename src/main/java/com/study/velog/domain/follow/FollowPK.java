package com.study.velog.domain.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowPK implements Serializable {
    private String followerId;
    private Long followeeId;
}