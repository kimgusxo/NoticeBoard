package com.example.noticeboard.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("photo")
public class Photo {

    @Id
    private Long photoId;

    @NotNull(message = "이미지가 존재하지 않습니다.")
    private String url;
    private Long boardId;

    @Transient
    private Board board;

}

