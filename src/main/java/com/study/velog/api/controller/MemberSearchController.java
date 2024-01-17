package com.study.velog.api.controller;

import com.study.velog.api.service.MemberSearchService;
import com.study.velog.api.service.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/search")
public class MemberSearchController {

    private final MemberSearchService memberSearchService;

    @GetMapping("/{memberId}")
    public MemberResponse searchMember(@PathVariable Long memberId)
    {
        return memberSearchService.searchMember(memberId);
    }

    @GetMapping("/all")
    public List<MemberResponse> searchAllMember(
            // TODO : Request Param (size, page)
    )
    {
        // PageRequest
        return memberSearchService.searchAllMember();
    }
}
