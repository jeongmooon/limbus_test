package com.example.test_web.service;

import com.example.test_web.db.DBConnection;
import com.example.test_web.global.util.HashUtil;
import com.example.test_web.global.util.HttpUtil;
import com.example.test_web.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.Map;

@Controller()
public class LoginService {
    @Autowired
    public DBConnection dbConnection;

    @GetMapping("/test")
    public String test(){
        return "3week/test";
    }

    @PostMapping("/test/login-auth-all")
    public String loginIdAuthAll(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session, HttpServletResponse res){
        String query = "SELECT COUNT(1) cnt FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"' AND PASS = '"+pass+"'";
        Map<String,Object> result = dbConnection.get(query);
        if(result.get("cnt") != null && Integer.parseInt(result.get("cnt").toString()) == 1){
            HttpUtil.setCookie(res, "userId", userId, 60*2);
            model.addAttribute("message","성공");
        } else {
            model.addAttribute("message","실패");
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-divide")
    public String loginIdAuthDivide(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        String query = "SELECT PASS FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"'";
        Map<String,Object> result = dbConnection.get(query);
        if(result.get("PASS") != null){
            if(pass.equals(result.get("PASS").toString())){
                model.addAttribute("message","성공");
            }   else {
                model.addAttribute("message","실패");
            }
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-all-newline")
    public String loginIdAuthAllNewLine(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        String query = "SELECT COUNT(1) cnt FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"'\n" +
                "AND PASS = '"+pass+"'";
        Map<String,Object> result = dbConnection.get(query);
        if(result.get("cnt") != null && Integer.parseInt(result.get("cnt").toString()) == 1){
            model.addAttribute("message","성공");
        } else {
            model.addAttribute("message","실패");
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-all-param-newline")
    public String loginIdAuthAllParamNewLineNewLine(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(1) cnt FROM USER_TEST_INFO WHERE (USER_ID =REPLACE(REPLACE('"+userId+"\n");
        query.append( "', '\n', ''), '\r', ''))");
        query.append("AND (PASS = REPLACE(REPLACE('"+pass+"\n");
        query.append("', '\n', ''), '\r', ''))");
        Map<String,Object> result = dbConnection.get(query.toString());
        if(result.get("cnt") != null && Integer.parseInt(result.get("cnt").toString()) == 1){
            model.addAttribute("message","성공");
        } else {
            model.addAttribute("message","실패");
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-all-hash")
    public String loginIdAuthAllHash(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        String query = "SELECT COUNT(1) cnt FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"' AND PASS = '"+HashUtil.getSha256(pass)+"'";
        Map<String,Object> result = dbConnection.get(query);
        if(result.get("cnt") != null && Integer.parseInt(result.get("cnt").toString()) == 1){
            model.addAttribute("message","성공");
        } else {
            model.addAttribute("message","실패");
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-divide-hash")
    public String loginIdAuthDivideHash(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        String query = "SELECT PASS FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"'";
        Map<String,Object> result = dbConnection.get(query);
        if(result.get("PASS") != null){
            if(HashUtil.getSha256(pass).equals(result.get("PASS").toString())){
                model.addAttribute("message","성공");
            }   else {
                model.addAttribute("message","실패");
            }
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-all-hash-param-newline")
    public String loginIdAuthAllHashParamNewLineNewLine(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(1) cnt FROM USER_TEST_INFO WHERE (USER_ID =REPLACE(REPLACE('"+userId+"\n");
        query.append( "', '\n', ''), '\r', ''))");
        query.append("AND (PASS = REPLACE(REPLACE('"+HashUtil.getSha256(pass)+"\n");
        query.append("', '\n', ''), '\r', ''))");
        Map<String,Object> result = dbConnection.get(query.toString());
        if(result.get("cnt") != null && Integer.parseInt(result.get("cnt").toString()) == 1){
            model.addAttribute("message","성공");
        } else {
            model.addAttribute("message","실패");
        }
        return "3week/login";
    }

    @PostMapping("/test/login-auth-all-hash-bcryte")
    public String loginIdAuthAllHashBCryte(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        String query = "SELECT SALT FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"'";
        Map<String,Object> result = dbConnection.get(query);

        if(result.get("SALT") != null){
            String salt = "$2a$10$"+HashUtil.baseEncodeSalt(result.get("SALT").toString());
            String byPass = HashUtil.getBCryptBySalt(pass,salt);

            query = "SELECT COUNT(1) cnt FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"' AND PASS = '"+byPass+"'";

            result = dbConnection.get(query);
            if(result.get("cnt") != null && Integer.parseInt(result.get("cnt").toString()) == 1){
                model.addAttribute("message","성공");
            } else {
                model.addAttribute("message","실패");
            }
        }

        return "3week/login";
    }

    @PostMapping("/test/login-auth-divide-hash-bcryte")
    public String loginIdAuthDivideHashBCryte(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session){
        String query = "SELECT SALT, PASS FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"'";
        Map<String,Object> result = dbConnection.get(query);

        if(result.get("SALT") != null){
            String salt = "$2a$10$"+HashUtil.baseEncodeSalt(result.get("SALT").toString());
            String byPass = HashUtil.getBCryptBySalt(pass,salt);

            if(result.get("PASS").toString().equals(byPass)){
                model.addAttribute("message","성공");
            } else {
                model.addAttribute("message","실패");
            }
        }

        return "3week/login";
    }

    @PostMapping("/test/login-auth-jwt")
    public String loginIdAuthJwt(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpServletRequest req, HttpServletResponse res){
        String query = "SELECT SALT, PASS FROM USER_TEST_INFO WHERE USER_ID ='"+userId+"'";
        Map<String,Object> result = dbConnection.get(query);

        if(result.get("SALT") != null){
            String salt = "$2a$10$"+HashUtil.baseEncodeSalt(result.get("SALT").toString());
            String byPass = HashUtil.getBCryptBySalt(pass,salt);

            if(result.get("PASS").toString().equals(byPass)){
                String ip = req.getRemoteAddr();
                String userAgent = req.getHeader("User-Agent");
                String deviceId = HashUtil.getSha256(ip + userAgent);
                String accessToken = JwtUtil.generateAccessToken(userId, deviceId,"USER");
                String refreshToken = JwtUtil.generateRefreshToken(userId, deviceId);

                // 엑세스 토큰 저장
                HttpUtil.setCookie(res, "accessToken", accessToken, (int) Duration.ofMinutes(15).getSeconds());

                // 리프레쉬 토큰 저장
                HttpUtil.setCookie(res, "refreshToken", refreshToken, (int) Duration.ofDays(7).getSeconds());

                // 디바이스ID 저장 리프레쉬랑 동일기간으로 설정
                HttpUtil.setCookie(res, "deviceId", deviceId, (int) Duration.ofDays(7).getSeconds());

                model.addAttribute("message","성공");
            } else {
                model.addAttribute("message","실패");
            }
        }

        return "3week/login";
    }
}
