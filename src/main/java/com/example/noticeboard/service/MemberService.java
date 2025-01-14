package com.example.noticeboard.service;

import com.example.noticeboard.domain.Member;
import com.example.noticeboard.repository.BoardRepository;
import com.example.noticeboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository,
                         BoardRepository boardRepository) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public Mono<Member> getOneMember(String id) {
        return memberRepository
                .findById(id)
                .flatMap(member -> boardRepository.findByMemberId(member.getMemberId())
                        .collectList()
                        .map(boards -> {
                            member.setBoardList(boards);
                            return member;
                        }));
    }

    public Flux<Member> getAllMember() {
        return memberRepository.findAll();
    }
}
