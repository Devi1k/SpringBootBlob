package com.example.springbootblog.dao;

import com.example.springbootblog.utils.PageQueryUtil;

public interface BlogLinkMapper {
    int getTotalLinks(PageQueryUtil pageUtil);
}
