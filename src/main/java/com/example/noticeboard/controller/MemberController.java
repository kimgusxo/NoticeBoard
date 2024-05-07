package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/get/{memberId}")
    public Mono<String> getOneMember(@PathVariable Long memberId, Model model) {
        model.addAttribute("member", memberService.getOneMember(memberId));
        return Mono.just("memberDetail");
    }

    @GetMapping("/get/members")
    public Mono<String> getAllMembers(Model model) {
        return memberService.getAllMember()
                .collectList()
                .doOnNext(members -> model.addAttribute("members", members))
                .thenReturn("memberList");
    }

    @PostMapping("/post/save")
    public Mono<String> createMember(@RequestBody Member member, Model model) {
        model.addAttribute("member", memberService.createMember(member));
        return Mono.just("memberDetail");
    }

}
