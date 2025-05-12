package com.example.test_web.domain.Identity.controller;

import com.example.test_web.domain.Identity.dto.IdentityRequestDTO;
import com.example.test_web.domain.Identity.dto.IdentityResponseDTO;
import com.example.test_web.domain.Identity.service.IdentityService;
import com.example.test_web.domain.sinner.service.SinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/identity")
public class IdentityController {

    private final IdentityService identityService;
    private final SinnerService sinnerService;

    @Autowired
    public IdentityController(IdentityService identityService, SinnerService sinnerService){
        this.identityService = identityService;
        this.sinnerService = sinnerService;
    }

    @GetMapping
    public String getLoginPage(Model model) throws Exception {
        model.addAttribute("sinnerList",sinnerService.getSinnerList());
        model.addAttribute("body", "identity/identity");
        return "layout";
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<IdentityResponseDTO> addIdentity(@ModelAttribute IdentityRequestDTO identityRequestDTO, @CookieValue("accessToken") String accessToken) throws Exception {
        identityService.insertIdentity(identityRequestDTO, accessToken);
        return ResponseEntity.ok(IdentityResponseDTO.builder().message("success").build());
    }
}
