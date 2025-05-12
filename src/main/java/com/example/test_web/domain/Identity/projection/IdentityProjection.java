package com.example.test_web.domain.Identity.projection;

public interface IdentityProjection {
    Long getIdentityId();    // i.id
    String getIdentityName(); // i.name
    Integer getIdentityGrade(); // i.grade
    String getIdentityImgPath(); // i.imgPath
    Integer getIsOwned();  // CASE WHEN (ui.id IS NOT NULL) THEN 1 ELSE 0 END
    Long getSinnerId();
    String getSinnerName(); // s.name
}