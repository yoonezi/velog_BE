package com.study.velog.api.controller.follow;

import com.study.velog.api.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@CrossOrigin("*")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followeeId}")
    public Long saveFollow(@PathVariable Long followeeId)
    {
        return followService.follow(followeeId);
    }

    @DeleteMapping("/{followeeId}")
    public void unFollow(@PathVariable Long followeeId)
    {
        followService.unFollow(followeeId);
    }
}
