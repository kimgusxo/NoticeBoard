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
import java.util.List;

@Data
@Table("board")
public class Board {

    @Id
    private Long boardId;

    @NotNull(message = "작성자가 Null이면 안됩니다.")
    private String writer;
    @NotNull(message = "제목이 Null이면 안됩니다.")
    private String title;
    @NotNull(message = "내용이 Null이면 안됩니다.")
    private String content;

    @PastOrPresent
    @CreatedDate
    private LocalDate registrationDate;

    @PastOrPresent
    @LastModifiedDate
    private LocalDate updateDate;

    private Long memberId;

    @Transient
    private List<Photo> photoList;

    @Transient
    private List<Reply> replyList;

}
