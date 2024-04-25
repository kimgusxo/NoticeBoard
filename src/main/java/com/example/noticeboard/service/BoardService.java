package com.example.noticeboard.service;

import com.example.noticeboard.domain.Board;
import com.example.noticeboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public Mono<Board> createBoard(Board board) {
        return boardRepository.save(board);
    }
}
