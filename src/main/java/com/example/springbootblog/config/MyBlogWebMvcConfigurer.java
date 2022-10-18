package com.example.springbootblog.config;

import com.example.springbootblog.interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyBlogWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:/Users/starry/Documents/Upload/");
    }


    public void addInterceptor(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("admin/login")
                .excludePathPatterns("admin/dist/**").excludePathPatterns("admin/plugins/**");
    }
}
