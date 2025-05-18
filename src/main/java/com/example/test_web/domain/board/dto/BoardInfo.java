package com.example.test_web.domain.board.dto;

import com.example.test_web.global.pagination.SliceElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardInfo implements SliceElement {
    private Long id;
    private String title;
    private String content;
    private LocalDate registDate;
    private Long view;
    private String userId;
}
