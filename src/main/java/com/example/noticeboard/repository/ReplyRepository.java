package com.example.noticeboard.repository;

import com.example.noticeboard.domain.Photo;
import com.example.noticeboard.domain.Reply;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReplyRepository extends ReactiveCrudRepository<Reply, Long> {

    Flux<Reply> findByBoardId(Long boardId);
}
