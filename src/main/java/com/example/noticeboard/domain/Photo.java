package com.example.noticeboard.domain;

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

    private String url;

    private Long boardId;

    @Transient
    private Board board;

}

