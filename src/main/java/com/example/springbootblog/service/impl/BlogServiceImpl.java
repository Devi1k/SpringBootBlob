package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogMapper;
import com.example.springbootblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public int getTotalBlogs() {
        return blogMapper.getTotalBlogs(null);
    }
}
