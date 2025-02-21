package org.example.config;

import org.example.controller.advice.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // @formatter:off
        registry.addInterceptor(new LoggerInterceptor())
        .addPathPatterns("/**");
        // @formatter:on
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // @formatter:off
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
        // @formatter:on
    }
}
