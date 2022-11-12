package com.example.springbootblog.controller.admin;

import com.example.springbootblog.entity.AdminUser;
import com.example.springbootblog.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CommentService commentService;
    @Resource
    private LinkService linkService;
    @Resource
    private TagService tagService;


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategories());
        request.setAttribute("blogCount", blogService.getTotalBlogs());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComments());
        return "admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
//        System.out.println(password);
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }
        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "用户名或密码错误");
            return "admin/login";
        }

    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer userId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(userId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String updateName(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer userId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateUserName(userId, loginUserName, nickName)) {
            return "success";
        } else {
            return "修改失败";
        }
    }


    @PostMapping("/profile/password")
    @ResponseBody
    public String updatePassword(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "密码不能为空";
        }
        Integer userId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(userId, originalPassword, newPassword)) {
            request.removeAttribute("loginUserId");
            request.removeAttribute("loginUser");
            request.removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.removeAttribute("loginUserId");
        request.removeAttribute("loginUser");
        request.removeAttribute("errorMsg");
        return "admin/login";
    }
}
