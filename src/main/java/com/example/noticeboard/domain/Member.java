package com.example.noticeboard.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("member")
public class Member {

    @Id
    private Long memberId;

    private String id;
    private String password;

    @Transient
    private List<Board> boardList;

    public void addBoard(Board board) {
        this.boardList.add(board);
    }
}
