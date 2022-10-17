package com.example.springbootblog.dao;

import com.example.springbootblog.utils.PageQueryUtil;

public interface BlobTagMapper {
    int getTotalTags(PageQueryUtil pageUtil);
}
