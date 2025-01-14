package com.example.noticeboard.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotNull(message = "댓글 작성자가 Null이면 안됩니다.")
    private String writer;
    @NotNull(message = "댓글 내용이 Null이면 안됩니다.")
    private String content;

    @PastOrPresent
    @CreatedDate
    private LocalDate registrationDate;

    @PastOrPresent
    @LastModifiedDate
    private LocalDate updateDate;

    private Long boardId;

    @Transient
    private Board board;

}
