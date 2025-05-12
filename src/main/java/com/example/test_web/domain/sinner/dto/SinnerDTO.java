package com.example.test_web.domain.sinner.dto;

import com.example.test_web.domain.sinner.entity.Sinner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SinnerDTO {
    Long id;
    String name;
    String image;

    public static SinnerDTO toDTO(Sinner sinner){
        return new SinnerDTO(sinner.getId(), sinner.getName(), sinner.getImage());
    }
}
