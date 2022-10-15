package com.example.springbootblob.controller;

import com.example.springbootblob.service.TransactionTestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TransactionTestController {
    @Resource
    private TransactionTestService transactionTestService;

    @GetMapping("/transactionTest")
    @ResponseBody
    public String transactionTest() {
        transactionTestService.test1();
        transactionTestService.test2();
        return "transactionTest";
    }
}
