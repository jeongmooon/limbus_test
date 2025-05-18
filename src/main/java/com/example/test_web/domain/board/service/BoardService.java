package com.example.test_web.domain.board.service;

import com.example.test_web.domain.board.dto.BoardDTO;
import com.example.test_web.domain.board.dto.BoardInfo;
import com.example.test_web.domain.board.dto.BoardRequestDTO;
import com.example.test_web.domain.board.entity.Board;
import com.example.test_web.domain.board.repository.BoardRepository;
import com.example.test_web.domain.user.entity.UserInfo;
import com.example.test_web.domain.user.repository.UserInfoRepository;
import com.example.test_web.global.exception.BizException;
import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.JsoupUtil;
import com.example.test_web.global.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BoardService {
    private final UserInfoRepository userInfoRepository;
    private final BoardRepository boardRepository;

    public List<BoardInfo> getBoardList(Pageable pageable){
        List<BoardInfo> boardList = boardRepository.findBoardInfos(pageable);
        return boardList;
    }

    public BoardDTO getBoard(String accessToken){
        Long userId = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        UserInfo user = userInfoRepository.getReferenceById(userId);
        return BoardDTO.builder().author(user.getGameCode()).build();
    }

    public String addBoard(BoardRequestDTO boardRequestDTO, String accessToken){
        Long userId = Long.parseLong(Objects.requireNonNull(JwtUtil.validate(accessToken)));
        UserInfo user = userInfoRepository.getReferenceById(userId);

        String content = boardRequestDTO.getContent();

        // 1. Jsoup로 이미지 태그 제거
        String sanitizedContent = JsoupUtil.replaceTag(content, "img").replaceAll("!\\[[^\\]]*\\]\\([^)]*\\)", "");

        // 3. script 등 XSS 위험 요소도 제거 (추가 보안)
        sanitizedContent = JsoupUtil.safeXss(sanitizedContent);

        // 4. 완전히 비었는지 체크 (필요 시)
        if (sanitizedContent.trim().isEmpty()) {
            throw new BizException(ErrorCode.INVALID_BOARD_CONTENT);
        }

        Board board = Board.createBoard(boardRequestDTO.getTitle(), sanitizedContent, user);

        boardRepository.save(board);
        return "success";
    }
}
