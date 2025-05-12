package com.example.test_web.domain.userIdentity.repository;

import com.example.test_web.domain.userIdentity.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
    @Modifying
    @Query("DELETE FROM UserIdentity ui WHERE ui.user.id = :userId AND ui.identity.id IN :identityIds")
    void deleteByUserIdAndIdentityIds(@Param("userId") Long userId, @Param("identityIds") List<Long> identityIds);

}
