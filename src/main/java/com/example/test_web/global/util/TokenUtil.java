package com.example.test_web.global.util;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    private static final Map<String, String> refreshTokens = new HashMap<>();

    public static void store(String userId, String refreshToken) {
        refreshTokens.put(userId, refreshToken);
    }

    public static String get(String userId) {
        return refreshTokens.get(userId);
    }
}
