package com.example.springbootblog.dao;

import com.example.springbootblog.utils.PageQueryUtil;

public interface BlogTagMapper {
    int getTotalTags(PageQueryUtil pageUtil);
}
