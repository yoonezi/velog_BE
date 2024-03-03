package com.study.velog.api.controller.follow;

import com.study.velog.api.controller.follow.dto.response.FollowStatusResponse;
import com.study.velog.config.AuthUtil;
import com.study.velog.config.exception.ApiException;
import com.study.velog.config.exception.ErrorCode;
import com.study.velog.domain.follow.Follow;
import com.study.velog.domain.follow.FollowRepository;
import com.study.velog.domain.member.Member;
import com.study.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/follow/status")
public class FollowStatusController {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/{followeeId}")
    public FollowStatusResponse findFollowStatus(@PathVariable Long followeeId)
    {
        Member member = memberRepository.findByEmail(AuthUtil.currentUserEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Optional<Follow> follow = followRepository.findByFollowerIdAndFolloweeId(member.getMemberId(), followeeId);

        return follow.map(FollowStatusResponse::of).orElseGet(() -> FollowStatusResponse.of(null));
    }
}
