package com.example.test_web.domain.deck.entity;

import com.example.test_web.domain.Identity.entity.Identity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "deck")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeckIdentity> deckIdentities = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_list_id")
    private DeckList deckList;

    public Deck(Long id, String name, List<DeckIdentity> deckIdentities, DeckList deckList){
        this.id = id;
        this.name = name;
        this.deckIdentities = deckIdentities;
        this.deckList = deckList;
    }
}
