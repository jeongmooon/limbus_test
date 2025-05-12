package com.example.test_web.domain.deck.repository;

import com.example.test_web.domain.deck.entity.DeckList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeckListRepository extends JpaRepository<DeckList, Long> {
    // 유저 ID로 해당 유저의 DeckList 전체 조회
    List<DeckList> findByUserId(Long userId);

    Optional<DeckList> findByUserIdAndUuid(Long userId, String uuid);

    // 유저 ID 기준으로 최신 등록 순으로 정렬
    List<DeckList> findByUserIdOrderByRegistDateDesc(Long userId);
}
