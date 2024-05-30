package com.example.noticeboard.repository;

import com.example.noticeboard.domain.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
    Mono<Boolean> existsById(String id);
    Mono<Member> findById(String id);

}
