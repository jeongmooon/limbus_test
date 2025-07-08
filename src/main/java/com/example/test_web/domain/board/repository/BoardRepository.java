package com.example.test_web.domain.board.repository;

import com.example.test_web.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository {
    Optional<Board> findByIdAndUserId(Long id, Long userId);
}
