package com.example.test_web.global.config;

import com.example.test_web.global.interceptor.LoginCheckInterceptor;
import com.example.test_web.global.interceptor.NavInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;
    private final NavInterceptor navInterceptor;

    @Autowired
    public WebConfig(LoginCheckInterceptor loginCheckInterceptor, NavInterceptor navInterceptor){
        this.loginCheckInterceptor = loginCheckInterceptor;
        this.navInterceptor = navInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/static/**",
                        "/favicon.ico"
                );

        registry.addInterceptor(navInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/static/**",
                        "/favicon.ico"
                );
         */
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
