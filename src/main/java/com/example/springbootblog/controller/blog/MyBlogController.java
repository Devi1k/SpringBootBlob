package com.example.springbootblog.controller.blog;

import com.example.springbootblog.service.BlogService;
import com.example.springbootblog.service.TagService;
import com.example.springbootblog.utils.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyBlogController {

    public static String theme = "amaze";

    @Resource
    private BlogService blogService;

    @Resource
    private TagService tagService;

    @GetMapping({"/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    @GetMapping("/page/{pageNum}")
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult pageResult = blogService.getBlogsForIndexPage(pageNum);
        if (pageResult == null) {
            return "error/error_404";
        }
        request.setAttribute("pageName", "首页");
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("blogPageResult", pageResult);
        return "blog/index";
//        return "blog/" + theme + "/index";
    }
}
