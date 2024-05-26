package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.service.AuthService;
import com.example.noticeboard.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public AuthController(AuthService authService, CustomUserDetailService customUserDetailService) {
        this.authService = authService;
        this.customUserDetailService = customUserDetailService;
    }

    @GetMapping("/showLogin")
    public Mono<String> showLogin() {
        return Mono.just("login");
    }

    @GetMapping("/showSignUp")
    public Mono<String> showSignUp() {
        return Mono.just("signUp");
    }

    @PostMapping("/login")
    public Mono<Void> login(@ModelAttribute Member member, ServerWebExchange exchange) {
        return customUserDetailService.findByUsername(member.getId())
                .filter(userDetails -> userDetails.getPassword().equals(member.getPassword()))
                .flatMap(userDetails -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                    return exchange.getSession()
                            .doOnNext(session -> session.getAttributes().put("SPRING_SECURITY_CONTEXT", context))
                            .then(Mono.defer(() -> exchange.getResponse().setComplete()));
                });
    }

    @PostMapping("/signUp")
    public Mono<String> signUp(@ModelAttribute Member member) {
        return authService.signUp(member).then(Mono.just("redirect:/board/showBoard"));
    }

    @GetMapping("/logout")
    public Mono<Void> logout(WebSession session) {
        return session.invalidate();
    }

}
