package com.example.test_web.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BoardRequestDTO {
    private Long id;
    private String title;
    private String content;
}
