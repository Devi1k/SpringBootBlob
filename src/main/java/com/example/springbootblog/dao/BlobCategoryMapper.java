package com.example.springbootblog.dao;

import com.example.springbootblog.utils.PageQueryUtil;

public interface BlobCategoryMapper {

    int getTotalCategories(PageQueryUtil pageUtil);
}
