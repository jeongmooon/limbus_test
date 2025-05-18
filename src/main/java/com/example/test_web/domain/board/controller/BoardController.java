package com.example.test_web.domain.board.controller;

import com.example.test_web.domain.board.dto.BoardDTO;
import com.example.test_web.domain.board.dto.BoardInfo;
import com.example.test_web.domain.board.dto.BoardRequestDTO;
import com.example.test_web.domain.board.service.BoardService;
import com.example.test_web.global.pagination.SliceResponse;
import com.example.test_web.global.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping()
    public String getBoardPage(
            @CookieValue("accessToken") String accessToken,
            Model model
    ){
        model.addAttribute("body", "board/board-list");
        return "layout";
    }

    @GetMapping("/list")
    public ResponseEntity<SliceResponse<BoardInfo>> getBoardList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        List<BoardInfo> boardInfos = boardService.getBoardList(pageable);
        Slice<BoardInfo> result =new SliceImpl<>(boardInfos, pageable, PaginationUtil.hasNext(boardInfos, pageable.getPageSize()));
        return ResponseEntity.ok(new SliceResponse<>(result));
    }

    @GetMapping("/write")
    public String getBoardWritePage(@CookieValue("accessToken") String accessToken, Model model){
        model.addAttribute("body", "board/board-write");
        model.addAttribute("boardDTO", boardService.getBoard(accessToken));
        return "layout";
    }

    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity<String> getBoardWrite(@RequestBody BoardRequestDTO boardRequestDTO, @CookieValue("accessToken") String accessToken){
        return ResponseEntity.ok(boardService.addBoard(boardRequestDTO, accessToken));
    }
}
