package com.example.noticeboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("reply")
public class Reply {

    @Id
    private Long replyId;

    private String writer;
    private String content;

    @CreatedDate
    private LocalDate registrationDate;

    @LastModifiedDate
    private LocalDate updateDate;

    private Long boardId;

    @Transient
    private Board board;

}
