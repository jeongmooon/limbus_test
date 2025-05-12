package com.example.test_web.domain.user.controller;

import com.example.test_web.domain.user.dto.UserCreateRequest;
import com.example.test_web.domain.user.dto.UserLoginRequest;
import com.example.test_web.domain.user.service.UserService;
import com.example.test_web.domain.user.validator.UserCreateRequestValidator;
import com.example.test_web.global.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) throws Exception{
        model.addAttribute("body", "auth/login");
        return "layout";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletResponse response) throws Exception{
        userService.logoutUser(response);
        return "redirect:/user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) throws Exception{
        model.addAttribute("body", "auth/register");
        return "layout";
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) throws Exception{
        return ResponseEntity.ok(userService.login(userLoginRequest,request,response));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserCreateRequest userCreateRequest, HttpServletRequest request, HttpServletResponse response) throws Exception{
        return ResponseEntity.ok(userService.registerUser(userCreateRequest,request,response));
    }
}
