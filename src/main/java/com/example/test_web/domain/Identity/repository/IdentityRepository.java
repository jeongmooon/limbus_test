package com.example.test_web.domain.Identity.repository;

import com.example.test_web.domain.Identity.entity.Identity;
import com.example.test_web.domain.Identity.projection.IdentityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

    @Query("""
        SELECT i.id AS identityId, 
               i.name AS identityName, 
               i.grade AS identityGrade, 
               i.imgPath AS identityImgPath, 
               CASE WHEN (ui.id IS NOT NULL) THEN 1 ELSE 0 END AS isOwned,
               s.id AS sinnerId,
               s.name AS sinnerName
        FROM Identity i
        JOIN i.sinner s
        LEFT JOIN UserIdentity ui ON ui.identity = i AND ui.user.id = :userId
        ORDER BY i.id
    """)
    List<IdentityProjection> findAllWithOwnershipByUserId(@Param("userId") Long userId);
}
