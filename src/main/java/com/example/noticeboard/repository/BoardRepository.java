package com.example.noticeboard.repository;

import com.example.noticeboard.domain.Board;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BoardRepository extends ReactiveCrudRepository<Board, Long> {
    Flux<Board> findByMemberId(Long memberId);
}
