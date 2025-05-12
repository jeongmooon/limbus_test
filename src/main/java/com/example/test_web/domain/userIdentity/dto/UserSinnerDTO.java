package com.example.test_web.domain.userIdentity.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserSinnerDTO {
    private Long id;
    private String name;
    private List<UserIdentityDTO> userIdentities;
}
