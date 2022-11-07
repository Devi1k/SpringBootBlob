package com.example.springbootblog.controller.blog;

import com.example.springbootblog.service.BlogService;
import com.example.springbootblog.service.TagService;
import com.example.springbootblog.utils.PageResult;
import com.example.springbootblog.vo.BlogDetailVO;
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
//        return "blog/index";
        return "blog/" + theme + "/index";
    }

    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return this.search(request, keyword, 1);
    }

    @GetMapping({"/search/{keyword}/{page}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {
        PageResult pageResult = blogService.getBlogsPageForSearch(keyword, page);
        request.setAttribute("blogPageResult", pageResult);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        return "blog/list";
    }

    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return this.category(request, categoryName, 1);
    }

    @GetMapping({"/category/{categoryName}/{page}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") Integer page) {
        PageResult pageResult = blogService.getBlogsPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", pageResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        return "blog/list";
    }

    @GetMapping({"/tag/{tagName}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName) {
        return this.tag(request, tagName, 1);
    }

    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(tagName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        return "blog/list";
    }


    @GetMapping("/blog/{blogId}")
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(blogId);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
        }
        request.setAttribute("pageName", "详情");
        return "blog/list";
    }
}
