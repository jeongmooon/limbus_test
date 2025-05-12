package com.example.test_web.domain.userIdentity.entity;

import com.example.test_web.domain.Identity.entity.Identity;
import com.example.test_web.domain.user.entity.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USER_IDENTITY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // USER_INFO의 PK
    private UserInfo user;

    // 인격
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_id")  // IDENTITY의 PK
    private Identity identity;

    private LocalDate registDate;
}
