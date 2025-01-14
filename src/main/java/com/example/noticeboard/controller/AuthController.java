package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.service.AuthService;
import com.example.noticeboard.service.CustomUserDetailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
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
    public Mono<Void> login(@ModelAttribute @Validated Member member, ServerWebExchange exchange) {
        return customUserDetailService.findByUsername(member.getId())
                .flatMap(userDetails -> {
                    if(!member.getPassword().equals(userDetails.getPassword())) {
                        return Mono.error(new RuntimeException("비밀번호가 잘못되었습니다."));
                    } else {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                        return exchange.getSession()
                                .doOnNext(session -> {
                                    session.getAttributes().put("SPRING_SECURITY_CONTEXT", context);
                                    session.getAttributes().put("ID", userDetails.getUsername());
                                })
                                .then(Mono.defer(() -> {
                                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                                    exchange.getResponse().getHeaders().setLocation(URI.create("/board/showBoard"));
                                    return exchange.getResponse().setComplete();
                                })
                                .onErrorResume(e -> Mono.error(e)));
                    }
                });
    }


    @PostMapping("/signUp")
    public Mono<String> signUp(@ModelAttribute @Validated Member member) {
        return authService.signUp(member).then(Mono.just("redirect:/board/showBoard"));
    }

    @GetMapping("/logout")
    public Mono<String> logout(WebSession session) {
        return session.invalidate().then(Mono.just("redirect:/board/showBoard"));
    }

}
