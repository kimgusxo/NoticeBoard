package com.example.noticeboard.service;

import com.example.noticeboard.domain.Board;
import com.example.noticeboard.repository.BoardRepository;
import com.example.noticeboard.repository.MemberRepository;
import com.example.noticeboard.repository.PhotoRepository;
import com.example.noticeboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository,
                        ReplyRepository replyRepository,
                        MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
        this.memberRepository = memberRepository;
    }


    public Mono<Board> createBoard(Board savedBoard) {
        return boardRepository.save(savedBoard)
                .flatMap(board -> memberRepository.findById(savedBoard.getMemberId())
                        .flatMap(member -> {
                            member.addBoard(board);
                            return memberRepository.save(member).thenReturn(board);
                        }));
    }

    public Flux<Board> getAllBoard() {
        return boardRepository
                .findAll()
                .flatMap(board -> replyRepository.findByBoardId(board.getBoardId())
                            .collectList()
                            .map(replies -> {
                                board.setReplyList(replies);
                                return board;
                            }));
    }

    public Mono<Board> getOneByBoardId(Long boardId) {
        return boardRepository
                .findById(boardId)
                .flatMap(board -> replyRepository.findByBoardId(board.getBoardId())
                        .collectList()
                        .map(replies -> {
                            board.setReplyList(replies);
                            return board;
                        }));
    }

}
