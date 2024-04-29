package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Reply;
import com.example.noticeboard.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/post/save")
    public Mono<Reply> createReply(@RequestBody Reply reply) {
        return replyService.createReply(reply);
    }

    @PutMapping("/put/update")
    public Mono<Reply> updateReply(@RequestBody Reply updateReply) {
        return replyService.updateReply(updateReply);
    }

    @DeleteMapping("/delete")
    public Mono<Void> deleteReply(@PathVariable Long replyId) {
        return replyService.deleteReply(replyId);
    }
}
