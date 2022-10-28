package com.example.springbootblog.service;

import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;

public interface BlogService {
    int getTotalBlogs();

    String saveBlog(Blog blog);

    String updateBlog(Blog blog);

    Blog getBlogById(Long blogId);

    PageResult getBlogsPage(PageQueryUtil pageQueryUtil);

    Boolean deleteBatch(Integer[] ids);
}
