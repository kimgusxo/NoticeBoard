package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Board;
import com.example.noticeboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/post/save")
    public Mono<Board> createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @GetMapping("/get/{boardId}")
    public Mono<Board> getOneByBoardId(@PathVariable Long boardId) {
        return boardService.getOneByBoardId(boardId);
    }

}
