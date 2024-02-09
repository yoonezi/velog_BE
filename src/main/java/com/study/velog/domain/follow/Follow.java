package com.study.velog.domain.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@IdClass(FollowPK.class)
public class Follow {

    @Id
    @Column(name = "follower_id", insertable = false, updatable = false)
    private Long followerId;

    @Id
    @Column(name = "followee_id", insertable = false, updatable = false)
    private Long followeeId;

    @Builder
    public Follow(Long followerId,  Long followeeId)
    {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }
}