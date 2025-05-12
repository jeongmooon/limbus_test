package com.example.test_web.domain.user.service;

import com.example.test_web.domain.user.dto.UserCreateRequest;
import com.example.test_web.domain.user.dto.UserLoginRequest;
import com.example.test_web.domain.user.entity.UserInfo;
import com.example.test_web.domain.user.repository.UserInfoRepository;
import com.example.test_web.domain.user.validator.UserCreateRequestValidator;
import com.example.test_web.global.exception.BizException;
import com.example.test_web.global.exception.ErrorCode;
import com.example.test_web.global.util.HashUtil;
import com.example.test_web.global.util.HttpUtil;
import com.example.test_web.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserService {
    private final UserCreateRequestValidator userCreateRequestValidator;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserService(UserInfoRepository userInfoRepository, UserCreateRequestValidator userCreateRequestValidator){
        this.userInfoRepository = userInfoRepository;
        this.userCreateRequestValidator = userCreateRequestValidator;
    }

    public String registerUser(UserCreateRequest userCreateRequest, HttpServletRequest request, HttpServletResponse response){
        String message = userCreateRequestValidator.validate(userCreateRequest);
        if (userInfoRepository.existsByUserId(userCreateRequest.getUserId())) {
            throw new BizException(ErrorCode.EXISTS_USER_ID);
        }

        String encodePass =  HashUtil.getBCrypt(userCreateRequest.getPass());
        String salt = HashUtil.getSalt(encodePass);

        UserInfo savedUserInfo = userInfoRepository.save(UserInfo.builder()
                .userId(userCreateRequest.getUserId())
                .pass(encodePass)
                .salt(salt)
                .gameCode(userCreateRequest.getLimbusGameCode())
                .role("USER")
                .build());

        if(savedUserInfo.getId() != null){
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            String deviceId = HashUtil.getSha256(ip + userAgent);
            String accessToken = JwtUtil.generateAccessToken(savedUserInfo.getId().toString(), deviceId, savedUserInfo.getRole());
            String refreshToken = JwtUtil.generateRefreshToken(savedUserInfo.getId().toString(), deviceId);

            // 엑세스 토큰 저장
            HttpUtil.setCookie(response, "accessToken", accessToken, (int) Duration.ofMinutes(15).getSeconds());

            // 리프레쉬 토큰 저장
            HttpUtil.setCookie(response, "refreshToken", refreshToken, (int) Duration.ofDays(7).getSeconds());

            // 디바이스ID 저장 리프레쉬랑 동일기간으로 설정
            HttpUtil.setCookie(response, "deviceId", deviceId, (int) Duration.ofDays(7).getSeconds());
        } else {
            throw new BizException(ErrorCode.EXCEPTION_USER_REGIST);
        }

        return "정상 가입 되었습니다.";
    }

    public String login(UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response){
        UserInfo user = userInfoRepository.findByUserId(userLoginRequest.getUserId())
                .orElseThrow(() -> new BizException(ErrorCode.EXCEPTION_USER_LOGIN));

        String encryptedInputPassword = HashUtil.getBCryptBySalt(userLoginRequest.getPass(), "$2a$10$"+HashUtil.baseEncodeSalt(user.getSalt()));

        if (!user.getPass().equals(encryptedInputPassword)) {
            throw new BizException(ErrorCode.EXCEPTION_USER_LOGIN);
        }

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String deviceId = HashUtil.getSha256(ip + userAgent);
        String accessToken = JwtUtil.generateAccessToken(user.getId().toString(), deviceId, user.getRole());
        String refreshToken = JwtUtil.generateRefreshToken(user.getId().toString(), deviceId);

        // 엑세스 토큰 저장
        HttpUtil.setCookie(response, "accessToken", accessToken, (int) Duration.ofMinutes(15).getSeconds());

        // 리프레쉬 토큰 저장
        HttpUtil.setCookie(response, "refreshToken", refreshToken, (int) Duration.ofDays(7).getSeconds());

        // 디바이스ID 저장 리프레쉬랑 동일기간으로 설정
        HttpUtil.setCookie(response, "deviceId", deviceId, (int) Duration.ofDays(7).getSeconds());

        return null;
    }

    public void logoutUser(HttpServletResponse response){
        HttpUtil.expireCookie(response, "accessToken");
        HttpUtil.expireCookie(response, "refreshToken");
        HttpUtil.expireCookie(response, "deviceId");
    }
}
