package com.example.test_web.global.interceptor;

import com.example.test_web.common.nav.dto.NavMenuDTO;
import com.example.test_web.common.nav.service.NavService;
import com.example.test_web.global.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class NavInterceptor implements HandlerInterceptor {
    private final NavService navService;

    @Autowired
    public NavInterceptor(NavService navService){
        this.navService = navService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<NavMenuDTO> menuList = navService.getMenuList(HttpUtil.getCookieValue(request, "accessToken"));
        request.setAttribute("menuList", menuList);
        return true;
    }
}
