package com.example.test_web.domain.deck.entity;

import com.example.test_web.domain.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "deck_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DeckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @OneToMany(mappedBy = "deckList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deck> deckList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // USER_INFO의 PK
    private UserInfo user;

    private LocalDate registDate;

    public void updateDeckList(List<Deck> deckList){
        if(deckList != null) {
            this.deckList.clear();
            for (Deck deck : deckList) {
                deck.setDeckList(this);       // ✅ 연관관계 주입
                this.deckList.add(deck);      // ✅ 컬렉션에도 추가
            }
        }
    }

    public DeckList(Long id, String uuid, List<Deck> deckList, UserInfo user, LocalDate registDate){
        this.id = id;
        this.uuid = uuid;
        this.deckList = deckList;
        this.registDate = registDate;
        this.user = user;
    }
}
