package com.example.test_web.domain.board.dto;

import com.example.test_web.global.pagination.SliceElement;
import lombok.*;

@Getter
@Builder
public class BoardDTO implements SliceElement {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String modifyYn;
}
