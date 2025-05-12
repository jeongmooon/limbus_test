package com.example.test_web.common.nav.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NavMenuDTO {
    private String url;
    private String name;
}
