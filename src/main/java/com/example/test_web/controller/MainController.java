package com.example.test_web.controller;

import com.example.test_web.common.youtube.service.YouTubeService;
import com.example.test_web.db.DBConnection;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    public DBConnection dbConnection;

    private final YouTubeService youTubeService;

    @Autowired
    public MainController(YouTubeService youTubeService){
        this.youTubeService = youTubeService;
    }

    @GetMapping("/main")
    public String index(Model model, HttpSession session) throws Exception{
        model.addAttribute("videoId", youTubeService.getLatestVideoByChannel());
        model.addAttribute("body", "main");
        return "layout";
    }

    @GetMapping("/login")
    public String getLoginPage() throws Exception{
        return "auth/login";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam("userId") String userId, @RequestParam("pass") String pass, Model model, HttpSession session) throws Exception{
        Map<String, Object> result = dbConnection.get("SELECT * FROM USER_INFO WHERE USER_ID = '"+userId + "' AND PASS = '"+pass+"'");

        if(result.get("USER_ID") == null) {
            model.addAttribute("result", result.get("USER_ID") == null);
            model.addAttribute("userId",userId);
            return "auth/login";
        } else {
            session.setAttribute("user", result.get("IDX"));
            session.setAttribute("userId", result.get("USER_ID"));
            return "redirect:/main";
        }
    }

    @GetMapping("/score")
    public String getLoginPage(@RequestParam("name") String name, Model model) throws Exception{
        Map<String, Object> result = dbConnection.get("SELECT * FROM test_table WHERE name = '"+name + "'");
        model.addAttribute("result", result);
        return "score";
    }
}
