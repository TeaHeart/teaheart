package org.example.config;

import org.example.controller.advice.AuthInterceptor;
import org.example.controller.advice.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private PrivilegeConfig privilegeConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new AuthInterceptor(privilegeConfig))
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/error", "/favicon.ico", "/index.html")
                .excludePathPatterns("/user/login", "/user/me");
    }
}
