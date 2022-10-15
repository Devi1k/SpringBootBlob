package com.example.springbootblob.controller;

import com.example.springbootblob.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RequestTestController {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @ResponseBody
    public String test1(String info) {
        if (StringUtils.isEmpty(info)) {
            return "请输入info值";
        }
        return "内容是" + info;
    }


    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ResponseBody
    public List<User> test2() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("十一");
        user1.setPassword("12121");
        User user2 = new User();
        user2.setId(2);
        user2.setName("十二");
        user2.setPassword("21212");
        User user3 = new User();
        user3.setId(3);
        user3.setName("十三");
        user3.setPassword("31313");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }
}
