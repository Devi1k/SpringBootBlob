package com.example.springbootblog.config;

import com.example.springbootblog.interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyBlogWebMvcConfigurer implements WebMvcConfigurer {
    @Resource
    private AdminLoginInterceptor adminLoginInterceptor;

    @Value("${upload.file.path}")
    private String uploadPath;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + uploadPath);
    }


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/dist/**").excludePathPatterns("/admin/plugins/**");
    }
}
