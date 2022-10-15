package com.example.springbootblob.controller;

import com.example.springbootblob.common.Constants;
import com.example.springbootblob.common.Result;
import com.example.springbootblob.common.ResultGenerator;
import com.example.springbootblob.entity.AdminUser;
import com.example.springbootblob.service.AdminUserService;
import com.example.springbootblob.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class AdminUserControler {
    @Resource
    private AdminUserService adminUserService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "参数异常！");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(adminUserService.getAdminUserPage(pageUtil));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody AdminUser user) {
        Result result = ResultGenerator.genFailResult("登录失败");
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            result.setMessage("请填写信息");
        }
        AdminUser loginUser = adminUserService.updateTokenAndLogin(user.getUserName(), user.getPassword());
        if (loginUser != null) {
            result = ResultGenerator.genSuccessResult(loginUser);
        }
        return result;
    }
}
