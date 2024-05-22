package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.service.AuthService;
import com.example.noticeboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<Void> login(@RequestBody Member member, WebFilterExchange webFilterExchange) {
        return authService.login(member)
                .flatMap(userDetails -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
                });
    }

    @PostMapping("/signUp")
    public Mono<String> signUp(@RequestBody Member member) {
        return authService.signUp(member).then(Mono.just("redirect:/board"));
    }

    @GetMapping("/logout")
    public Mono<Void> logout(WebSession session) {
        return session.invalidate();
    }

}
