package com.example.test_web.domain.sinner.entity;

import com.example.test_web.domain.sinner.dto.SinnerDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "sinner")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Sinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    public static Sinner toEntity(SinnerDTO dto){
        return Sinner.builder()
                .name(dto.getName())
                .build();
    }
}
