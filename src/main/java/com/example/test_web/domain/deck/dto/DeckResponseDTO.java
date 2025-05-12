package com.example.test_web.domain.deck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
public class DeckResponseDTO {
    private String message;
}
