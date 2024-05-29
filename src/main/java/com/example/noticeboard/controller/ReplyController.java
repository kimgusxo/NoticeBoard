package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Reply;
import com.example.noticeboard.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/post/save")
    public Mono<String> createReply(@ModelAttribute Reply savedReply) {
        return replyService.createReply(savedReply)
                .map(reply -> "redirect:/board/get/" + reply.getBoardId());
    }

    @PutMapping("/put/update")
    public Mono<String> updateReply(@ModelAttribute Reply updateReply, Model model) {
        model.addAttribute("reply", replyService.updateReply(updateReply));
        return Mono.just("boardDetail");
    }

    @DeleteMapping("/delete/{replyId}")
    public Mono<String> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return Mono.just("deleteNotification");
    }
}
