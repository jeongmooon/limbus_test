package com.example.test_web.domain.userIdentity.dto;

import com.example.test_web.domain.Identity.entity.Identity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserIdentityDTO {
    private Long id;
    private String name;
    private Integer grade;
    private String imgPath;
    private boolean isOwned;

    public static UserIdentityDTO toEntity(Identity identity){
        return UserIdentityDTO.builder()
                .id(identity.getId())
                .name(identity.getName())
                .imgPath(identity.getImgPath())
                .build();
    }
}
