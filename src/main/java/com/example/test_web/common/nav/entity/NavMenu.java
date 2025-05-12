package com.example.test_web.common.nav.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nav_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NavMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String name;
    private String role;
}
