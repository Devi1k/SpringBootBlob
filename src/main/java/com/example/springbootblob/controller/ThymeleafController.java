package com.example.springbootblob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ThymeleafController {
    @GetMapping("/thymeleaf")
    public String hello(HttpServletRequest httpServletRequest, @RequestParam(value = "description", required = false, defaultValue = "springboot-thymeleaf") String description) {
        httpServletRequest.setAttribute("description", description);
        return "thymeleaf";
    }

    @GetMapping("/attributes")
    public String attributes(ModelMap modelMap) {
        modelMap.put("title", "Thymeleaf 标签演示");
        modelMap.put("th_id", "thymeleaf-input");
        modelMap.put("th_name", "thymeleaf-input");
        modelMap.put("th_value", "13");
        modelMap.put("th_class", "thymeleaf-class");
        modelMap.put("th_href", "http://13blog.site");
        return "attributes";
    }

    @GetMapping("/simple")
    public String simple(ModelMap modelMap) {
        modelMap.put("thymeleafText", "nzp");
        modelMap.put("number1", 2019);
        modelMap.put("number2", 3);
        return "simple";
    }

    @GetMapping("/test")
    public String test(ModelMap modelMap) {
        modelMap.put("title", "Thymeleaf 语法测试");
        modelMap.put("testString", "玩转 Spring Boot");
        modelMap.put("bool", true);
        modelMap.put("testArray", new Integer[]{2018, 2019, 2020, 2021});
        modelMap.put("testList", Arrays.asList("Spring", "Spring Boot", "Thymeleaf", "MyBatis", "Java"));
        Map testMap = new HashMap();
        testMap.put("platform", "shiyanlou");
        testMap.put("title", "玩转 Spring Boot");
        testMap.put("author", "十三");
        modelMap.put("testMap", testMap);
        modelMap.put("testDate", new Date());
        return "test";
    }
}
