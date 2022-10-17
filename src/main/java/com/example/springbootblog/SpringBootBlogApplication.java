package com.example.springbootblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootblog.dao")
public class SpringBootBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBlogApplication.class, args);
    }

}
