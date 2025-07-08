package com.example.test_web.domain.board.entity;

import com.example.test_web.domain.board.dto.BoardDTO;
import com.example.test_web.domain.board.dto.BoardRequestDTO;
import com.example.test_web.domain.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDate registDate;
    private Long view;
    private String deleteDvsn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // USER_INFO의 PK
    private UserInfo user;

    public void updateView(){
        if (this.view == null) {
            this.view = 1L;
        } else {
            this.view++;
        }
    }

    public void updateBoard(BoardRequestDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public static Board createBoard(String title, String content, UserInfo user){
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .registDate(LocalDate.now())
                .view(0L)
                .deleteDvsn("N")
                .build();
    }

    public static BoardDTO toEntity(Board board){
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getUser().getUserId())
                .build();
    }

    public static BoardDTO toEntity(Board board, String userId){
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getUser().getUserId())
                .modifyYn(board.getUser().getUserId().equals(userId) ? "Y" : "N")
                .build();
    }
}
