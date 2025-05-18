package com.example.test_web.domain.board.repository;

import com.example.test_web.domain.board.dto.BoardInfo;
import com.example.test_web.domain.board.entity.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    List<BoardInfo> findBoardInfos(Pageable pageable);
}
