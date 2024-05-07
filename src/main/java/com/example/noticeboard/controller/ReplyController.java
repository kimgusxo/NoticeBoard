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
    public Mono<String> createReply(@RequestBody Reply reply, Model model) {
        model.addAttribute("reply", replyService.createReply(reply));
        return Mono.just("replyDetail");
    }

    @PutMapping("/put/update")
    public Mono<Reply> updateReply(@RequestBody Reply updateReply) {
        return replyService.updateReply(updateReply);
    }

    @DeleteMapping("/delete/{replyId}")
    public Mono<Void> deleteReply(@PathVariable Long replyId) {
        return replyService.deleteReply(replyId);
    }
}
