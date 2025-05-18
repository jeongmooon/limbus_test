package com.example.test_web.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "USER_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String pass;
    private String salt;
    private String gameCode;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    private String role;

    public UserInfo(Long id, String userId, String pass, String salt, String gameCode, String role){
        this.id = id;
        this.userId = userId;
        this.pass = pass;
        this.salt = salt;
        this.gameCode = gameCode;
        this.role = role;
    }
}
