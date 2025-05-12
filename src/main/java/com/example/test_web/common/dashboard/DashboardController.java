package com.example.test_web.common.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/{pageType}")
    public String getIdentityDashBoardPage(@PathVariable("pageType") String pageType, Model model){
        model.addAttribute("body", "dashboard/identityDashboard");
        return "layout";
    }
}
