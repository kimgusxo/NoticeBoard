package com.example.noticeboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;


import java.time.LocalDate;
import java.util.List;

@Data
@Table("board")
public class Board {

    @Id
    private Long boardId;

    private String writer;
    private String title;
    private String content;
    @CreatedDate
    private LocalDate registrationDate;
    @LastModifiedDate
    private LocalDate updateDate;

    private Long memberId;

    @Transient
    private List<Photo> photoList;
    @Transient
    private List<Reply> replyList;

}
