package com.example.test_web.domain.deck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeckIdentityDTO {
    private String imgPath;
    private Long sinnerId;
    private Long identityId;
}
