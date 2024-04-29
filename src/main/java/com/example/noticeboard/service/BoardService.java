package com.example.noticeboard.service;

import com.example.noticeboard.domain.Board;
import com.example.noticeboard.repository.BoardRepository;
import com.example.noticeboard.repository.PhotoRepository;
import com.example.noticeboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final PhotoRepository photoRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository,
                        PhotoRepository photoRepository,
                        ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.photoRepository = photoRepository;
        this.replyRepository = replyRepository;
    }


    public Mono<Board> createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Mono<Board> getOneByBoardId(Long boardId) {
        return boardRepository
                .findById(boardId)
                .flatMap(board -> photoRepository.findByBoardId(board.getBoardId())
                        .collectList()
                        .map(photos -> {
                            board.setPhotoList(photos);
                            return board;
                        }))
                .flatMap(board -> replyRepository.findByBoardId(board.getBoardId())
                        .collectList()
                        .map(replies -> {
                            board.setReplyList(replies);
                            return board;
                        }));
    }

}
