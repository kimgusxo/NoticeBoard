package com.example.noticeboard.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("member")
public class Member {

    @Id
    private Long memberId;

    @Length(message = "아이디의 길이는 6에서 12사이의 값이어야 합니다.", min=6, max=12)
    @NotNull(message = "아이디가 Null이면 안됩니다.")
    private String id;
    @Length(message = "비밀번호의 길이는 6에서 12사이의 값이어야 합니다.", min=6, max=12)
    @NotNull(message = "비밀번호가 Null이면 안됩니다.")
    private String password;

    @Transient
    private List<Board> boardList;

}
