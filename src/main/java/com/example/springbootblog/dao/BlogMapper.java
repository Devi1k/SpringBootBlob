package com.example.springbootblog.dao;

import com.example.springbootblog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {
    int getTotalBlogs(PageQueryUtil pageUtil);
}
