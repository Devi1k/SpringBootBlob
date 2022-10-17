package com.example.springbootblog.dao;

import com.example.springbootblog.utils.PageQueryUtil;

public interface BlobLinkMapper {
    int getTotalLinks(PageQueryUtil pageUtil);
}
