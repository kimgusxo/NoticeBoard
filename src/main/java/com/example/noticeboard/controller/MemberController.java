package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public Mono<Member> createMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }

    @GetMapping("/member/{memberId}")
    public Mono<Member> getOneMember(@PathVariable Long memberId) {
        return memberService.getOneMember(memberId);
    }

    @GetMapping("/members")
    public Flux<Member> getAllMembers() {
        return memberService.getAllMember();
    }

}
