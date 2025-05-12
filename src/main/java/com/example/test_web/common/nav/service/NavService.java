package com.example.test_web.common.nav.service;

import com.example.test_web.common.nav.dto.NavMenuDTO;
import com.example.test_web.common.nav.entity.NavMenu;
import com.example.test_web.common.nav.repository.NavMenuRepository;
import com.example.test_web.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NavService {

    private final NavMenuRepository navMenuRepository;

    @Autowired
    public NavService(NavMenuRepository navMenuRepository){
        this.navMenuRepository = navMenuRepository;
    }

    public List<NavMenuDTO> getMenuList(String accessToeken){
        if(accessToeken == null){
            return new ArrayList<NavMenuDTO>();
        }
        String role = JwtUtil.getTokenClaim(accessToeken, "userType");
        List<NavMenu> menuList = navMenuRepository.findAll();

        return menuList.stream()
                .filter(navMenu -> role.equals("ADMIN") || "USER".equals(navMenu.getRole()))
                .map(navMenu -> NavMenuDTO.builder()
                        .name(navMenu.getName())
                        .url(navMenu.getUrl())
                        .build())
                .toList();
    }
}
