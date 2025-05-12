package com.example.test_web.domain.Identity.entity;

import com.example.test_web.domain.Identity.dto.IdentityDTO;
import com.example.test_web.domain.Identity.dto.IdentityRequestDTO;
import com.example.test_web.domain.sinner.entity.Sinner;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "IDENTITY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Identity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer season;
    private String imgPath;
    private Integer grade;
    private LocalDate releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinner_id")
    private Sinner sinner;

    public void uploadImgPath(String imgPath){
        this.imgPath = imgPath;
    }

    public static Identity toEntity(IdentityDTO dto) {
        return Identity.builder()
                .name(dto.getName())
                .season(dto.getSeason())
                .imgPath(dto.getImgPath())
                .grade(dto.getGrade())
                .build();
    }

    public static Identity toEntity(IdentityRequestDTO dto, Sinner sinner) {
        return Identity.builder()
                .name(dto.getName())
                .season(dto.getSeason())
                .grade(dto.getGrade())
                .sinner(sinner)
                .releaseDate(LocalDate.now()) // 또는 원하는 방식
                .build();
    }
}
