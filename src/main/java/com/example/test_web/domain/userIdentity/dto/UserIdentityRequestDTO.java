package com.example.test_web.domain.userIdentity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class UserIdentityRequestDTO {
    private List<Long> addIdentity;
    private List<Long> deleteIdentity;
}
