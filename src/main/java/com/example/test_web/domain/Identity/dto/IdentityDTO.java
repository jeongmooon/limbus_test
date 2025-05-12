package com.example.test_web.domain.Identity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IdentityDTO implements Serializable {
    private String name;
    private String sinnerName;
    private Integer season;
    private String imgPath;
    private Integer grade;
}
