package com.example.springbootblob.controller;

import com.example.springbootblob.common.Result;
import com.example.springbootblob.common.ResultGenerator;
import com.example.springbootblob.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.*;

@Controller
@RequestMapping("/api")
public class ApiController {
    static Map<Integer, User> userMap = Collections.synchronizedMap(new HashMap<Integer, User>());

    static {
        User user = new User();
        user.setId(2);
        user.setName("user1");
        user.setPassword("123456");
        User user2 = new User();
        user2.setId(5);
        user2.setName("13-5");
        user2.setPassword("4");
        User user3 = new User();
        user3.setId(6);
        user3.setName("12");
        user3.setPassword("123");
        userMap.put(2, user);
        userMap.put(5, user2);
        userMap.put(6, user3);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> getOne(@PathVariable("id") Integer id) {
        if (id == null || id < 1) {
            return ResultGenerator.genFailResult("缺少参数");
        }
        User user = userMap.get(id);
        if (user == null) {
            return ResultGenerator.genFailResult("无此数据");
        }
        return ResultGenerator.genSuccessResult(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<User>> queryAll() {
        List<User> users = new ArrayList<User>(userMap.values());
        if (users.size() > 0) {
            return ResultGenerator.genSuccessResult(users);
        } else {
            return ResultGenerator.genFailResult("数据表为空");
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> insert(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getId().toString()) || StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.genFailResult("缺少参数");
        }
        if (userMap.containsKey(user.getId())) {
            return ResultGenerator.genFailResult("重复id");
        }
        userMap.put(user.getId(), user);
        return ResultGenerator.genSuccessResult(true);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody
    public Result<Boolean> update(@RequestBody User tempUser) {
        //参数验证
        if (tempUser.getId() == null || tempUser.getId() < 1 || StringUtils.isEmpty(tempUser.getName()) || StringUtils.isEmpty(tempUser.getPassword())) {
            return ResultGenerator.genFailResult("缺少参数");
        }
        //实体验证，不存在则不继续修改操作
        User user = userMap.get(tempUser.getId());
        if (user == null) {
            return ResultGenerator.genFailResult("参数异常");
        }
        user.setName(tempUser.getName());
        user.setPassword(tempUser.getPassword());
        userMap.put(tempUser.getId(), tempUser);
        return ResultGenerator.genSuccessResult(true);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Boolean> delete(@PathVariable Integer id) {
        if (id == null || id < 1) {
            return ResultGenerator.genFailResult("缺少参数");
        }
        userMap.remove(id);
        return ResultGenerator.genSuccessResult(true);
    }

}
