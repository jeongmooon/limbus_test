package com.example.test_web.domain.deck.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class DeckRequestDTO {
    private String uuid;
    private String name;
    private List<Long> identityList;
}
