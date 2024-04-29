package com.example.noticeboard.service;

import com.example.noticeboard.domain.Reply;
import com.example.noticeboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }


    public Mono<Reply> createReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public Mono<Reply> updateReply(Reply updateReply) {
        return replyRepository
                .findById(updateReply.getReplyId())
                .flatMap(reply -> {
                    reply.setContent(updateReply.getContent());
                    return replyRepository.save(reply);
                });
    }

    public Mono<Void> deleteReply(Long replyId) {
        return replyRepository.deleteById(replyId);
    }
}
