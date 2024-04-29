package com.example.noticeboard.repository;

import com.example.noticeboard.domain.Photo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PhotoRepository extends ReactiveCrudRepository<Photo, Long> {
    Flux<Photo> findByBoardId(Long boardId);
}
