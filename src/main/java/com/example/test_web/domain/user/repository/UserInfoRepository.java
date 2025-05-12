package com.example.test_web.domain.user.repository;

import com.example.test_web.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    boolean existsByUserId(String userId);
    Optional<UserInfo> findByUserId(String userId);
}
