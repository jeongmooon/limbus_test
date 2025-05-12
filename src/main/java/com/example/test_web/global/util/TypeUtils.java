package com.example.test_web.global.util;

import java.util.Set;

public final class TypeUtils {
    public static final Set<String> ALLOWED_TYPES = Set.of(
            "분노", "색욕", "나태", "탐식", "우울", "오만", "질투"
    );

    public static final Set<String> ALLOWED_SINNER = Set.of(
            "이상", "파우스트", "돈키호테", "료슈", "뫼르소", "홍루", "히스클리프", "이스마엘", "로쟈", "싱클레어", "오티스", "그레고르"
    );

    public static final Set<Integer> ALLOWED_GRADE = Set.of(
            1,2,3
    );

    public static final Set<Integer> ALLOWED_SEASON = Set.of(
            1,2,3,4,5
    );
    
    public static final Set<String> ACTIVE_COND = Set.of(
            "보유","공명"      
    );
    public static final Set<String> ALLOWED_IMG_CONTENT_TYPE = Set.of(
//            "image/png", "image/jpeg", "image/webp"
            "image/webp"
    );
}
