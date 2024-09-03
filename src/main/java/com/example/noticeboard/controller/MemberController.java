package com.example.noticeboard.controller;

import com.example.noticeboard.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Validated
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/showMember")
    public Mono<String> showMember(Model model, ServerWebExchange exchange) {
        return exchange.getSession().flatMap(session ->{
            String id = session.getAttribute("ID");
            return memberService.getOneMember(id)
                    .map(member -> {
                        model.addAttribute("member", member);
                        return "memberDetail";
                    });
        });
    }

    @GetMapping("/get/{id}")
    public Mono<String> getOneMember(@PathVariable @Valid String id, Model model) {
        model.addAttribute("member", memberService.getOneMember(id));
        return Mono.just("memberDetail");
    }

    @GetMapping("/get/members")
    public Mono<String> getAllMembers(Model model) {
        return memberService.getAllMember()
                .collectList()
                .doOnNext(members -> model.addAttribute("members", members))
                .thenReturn("memberList");
    }

}
