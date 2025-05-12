package com.example.test_web.domain.userIdentity.controller;

import com.example.test_web.domain.userIdentity.dto.UserDictionaryDTO;
import com.example.test_web.domain.userIdentity.dto.UserIdentityRequestDTO;
import com.example.test_web.domain.userIdentity.dto.UserIdentityResponseDTO;
import com.example.test_web.domain.userIdentity.dto.UserSinnerDTO;
import com.example.test_web.domain.userIdentity.service.UserIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user-identity")
public class UserIdentityController {

    private final UserIdentityService userIdentityService;

    @Autowired
    public UserIdentityController(UserIdentityService userIdentityService){
        this.userIdentityService = userIdentityService;
    }

    @GetMapping()
    public String getUserIdentityPage(Model model){
        model.addAttribute("body", "userIdentity/userIdentity");
        return "layout";
    }

    @ResponseBody
    @GetMapping("/main")
    public UserDictionaryDTO getUserIdentity(@CookieValue("accessToken") String accessToken){
        return userIdentityService.getUsreIdentity(accessToken);
    }

    @ResponseBody
    @PostMapping("/")
    public ResponseEntity<UserIdentityResponseDTO> addUserIdentity(@RequestBody UserIdentityRequestDTO userIdentityRequestDTO, @CookieValue("accessToken") String accessToken){
        userIdentityService.insertUsreIdentity(userIdentityRequestDTO, accessToken);
        return ResponseEntity.ok(UserIdentityResponseDTO.builder().message("success").build());
    }
}
