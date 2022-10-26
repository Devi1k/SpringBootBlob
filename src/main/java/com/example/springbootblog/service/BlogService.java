package com.example.springbootblog.service;

import com.example.springbootblog.entity.Blog;

public interface BlogService {
    int getTotalBlogs();

    String saveBlog(Blog blog);

    String updateBlog(Blog blog);

    Blog getBlogById(Long blogId);
}
