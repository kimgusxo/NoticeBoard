package com.example.noticeboard.service;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final MemberRepository memberRepository;

    @Autowired
    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Mono<Member> signUp(Member member) {
        return memberRepository.existsById(member.getId())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new RuntimeException("이미 존재하는 아이디입니다."));
                    }
                    return memberRepository.save(member);
                });
    }

}
