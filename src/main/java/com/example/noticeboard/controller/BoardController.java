package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Board;
import com.example.noticeboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/showBoard")
    public Mono<String> showBoard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }

        return boardService.getAllBoard()
                .collectList()
                .doOnNext(boards -> model.addAttribute("boards", boards))
                .thenReturn("boardPage");
    }

    @GetMapping("/showCreateBoard")
    public Mono<String> showCreateBoard() {
        return Mono.just("createBoard");
    }

    @PostMapping("/post/save")
    public Mono<String> createBoard(@ModelAttribute Board board, Model model) {
        model.addAttribute("board", boardService.createBoard(board));
        return Mono.just("boardDetail");
    }

    @GetMapping("/get/{boardId}")
    public Mono<String> getOneByBoardId(@PathVariable Long boardId, Model model) {
        model.addAttribute("board", boardService.getOneByBoardId(boardId));
        return Mono.just("boardDetail");
    }

}
