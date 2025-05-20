package com.example.test_web.common.error.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpServletResponse.SC_NOT_FOUND) {
                return "error/404"; // templates/error/404.html
            }
            if (statusCode == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
                return "error/500"; // templates/error/500.html
            }
        }

        return "error/404"; // 기타 에러
    }
}
