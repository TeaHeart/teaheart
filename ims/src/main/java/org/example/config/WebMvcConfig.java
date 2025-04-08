package org.example.config;

import org.example.controller.AuthInterceptor;
import org.example.controller.LoggerInterceptor;
import org.example.controller.PrivilegeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login");
        registry.addInterceptor(new PrivilegeInterceptor())
                .addPathPatterns("/api/**");
    }
}
