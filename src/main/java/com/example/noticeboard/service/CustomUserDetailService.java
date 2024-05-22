package com.example.noticeboard.service;

import com.example.noticeboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailService implements ReactiveUserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public CustomUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return memberRepository.findById(username)
                .map(member -> User.withUsername(member.getId())
                                .password(member.getPassword())
                                .roles("USER")
                                .build());
    }
}
