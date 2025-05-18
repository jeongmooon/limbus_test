package com.example.test_web.global.interceptor;

import com.example.test_web.domain.user.entity.UserInfo;
import com.example.test_web.domain.user.repository.UserInfoRepository;
import com.example.test_web.global.exception.BizException;
import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.HashUtil;
import com.example.test_web.global.util.HttpUtil;
import com.example.test_web.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public LoginCheckInterceptor(UserInfoRepository userInfoRepository){
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        String accessToken = HttpUtil.getCookieValue(request, "accessToken");
        String refreshToken = HttpUtil.getCookieValue(request, "refreshToken");

        // ✅ 로그인 상태일 때 로그인/회원가입 페이지 접근 차단
        if ((accessToken != null || refreshToken != null) && (uri.equals("/user/login") || uri.equals("/user/register"))) {
            response.sendRedirect("/main");
            return false;
        }

        // ✅ 로그인 안 한 경우 → 토큰 검사
        if (!isPublicPath(uri)) {
            if(accessToken != null){
                String username = JwtUtil.validate(accessToken);

                if (username != null) {
                    return true;
                }
            }


            // AccessToken 만료 → RefreshToken 유효 시 재발급
            if (refreshToken != null) {
                String refreshUserId = JwtUtil.validate(refreshToken);
                String refreshTokenType = JwtUtil.getTokenClaim(refreshToken, "tokenType");
                String refreshDeviceId = JwtUtil.getTokenClaim(refreshToken, "deviceId");
                String ip = request.getRemoteAddr();
                String userAgent = request.getHeader("User-Agent");
                String deviceId = HashUtil.getSha256(ip + userAgent);
                String cookieDeviceId = HttpUtil.getCookieValue(request, "deviceId");

                if ("refresh".equals(refreshTokenType) && refreshDeviceId.equals(cookieDeviceId) && refreshUserId != null) {
                    UserInfo user = userInfoRepository.findById(Long.parseLong(refreshUserId))
                            .orElseThrow(() -> new BizException(ErrorCode.EXCEPTION_USER_LOGIN));
                    String newAccessToken = JwtUtil.generateAccessToken(user.getId().toString(), deviceId, user.getRole());
                    HttpUtil.setCookie(response, "accessToken", newAccessToken, (int) Duration.ofMinutes(15).getSeconds());
                    response.sendRedirect(uri);
                    return false;
                }
            }

            // 둘 다 없음 → 로그인 페이지로
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }

    private boolean isPublicPath(String uri) {
        return uri.equals("/user/login") ||
                uri.equals("/user/register") ||
                uri.startsWith("/static/") ||
                uri.startsWith("/public/") ||
                uri.startsWith("/js/") ||           // 추가
                uri.startsWith("/css/") ||          // 추가
                uri.startsWith("/image/") ||       // 필요시 추가
                uri.equals("/error") ||
                uri.startsWith("/test");
    }
}
