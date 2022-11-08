package com.example.springbootblog.service;

import com.example.springbootblog.controller.vo.BlogDetailVO;
import com.example.springbootblog.controller.vo.SimpleBlogListVO;
import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;

import java.util.List;

public interface BlogService {
    int getTotalBlogs();

    String saveBlog(Blog blog);

    String updateBlog(Blog blog);

    Blog getBlogById(Long blogId);

    PageResult getBlogsPage(PageQueryUtil pageQueryUtil);

    Boolean deleteBatch(Integer[] ids);

    List<SimpleBlogListVO> getBlogListForIndexPage(int type);

    PageResult getBlogsForIndexPage(int page);

    PageResult getBlogsPageForSearch(String keyword, int page);

    PageResult getBlogsPageByCategory(String categoryName, int page);

    PageResult getBlogsPageByTag(String tagName, int page);

    public BlogDetailVO getBlogDetail(Long blogId);

}
