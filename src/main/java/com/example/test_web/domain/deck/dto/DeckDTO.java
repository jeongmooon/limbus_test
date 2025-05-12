package com.example.test_web.domain.deck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DeckDTO {
    private String name;
    private List<DeckIdentityDTO> deckIdentity;
}
