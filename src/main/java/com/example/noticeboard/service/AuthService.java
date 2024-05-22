package com.example.noticeboard.service;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public AuthService(MemberRepository memberRepository,
                       CustomUserDetailService customUserDetailService) {
        this.memberRepository = memberRepository;
        this.customUserDetailService = customUserDetailService;
    }

    public Mono<UserDetails> login(Member member) {
        return customUserDetailService.findByUsername(member.getId())
                .filter(userDetails ->
                        userDetails.getPassword().equals(member.getPassword()));
    }

    public Mono<Member> signUp(Member member) {
        return memberRepository.save(member);
    }

}
