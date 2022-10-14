package com.example.springbootblob.controller;

import com.example.springbootblob.dao.UserDao;
import com.example.springbootblob.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MyBatisController {
    @Resource
    UserDao userDao;

    @GetMapping("/users/mybatis/queryAll")
    @ResponseBody
    public List<User> queryAll() {
        return userDao.findAllUsers();
    }

    @GetMapping("/users/mybatis/insert")
    @ResponseBody
    public Boolean insert(String name, String password) {
        if (name.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        int res = userDao.insertUser(user);
        return res > 0;
    }

    @GetMapping("/users/mybatis/update")
    @ResponseBody
    public Boolean update(Integer id, String name, String password) {
        if (id == null || name.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        return userDao.updateUser(user) > 0;
    }

    @GetMapping("/users/mybatis/delete")
    @ResponseBody
    public Boolean delete(Integer id) {
        if (id == null || id < 1) {
            return false;
        }
        return userDao.deleteUser(id) > 0;
    }


}
