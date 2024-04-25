package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Board;
import com.example.noticeboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/board")
    public Mono<Board> createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

}
