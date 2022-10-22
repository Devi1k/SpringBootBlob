package com.example.springbootblog.service;

import com.example.springbootblog.entity.BlogTagCount;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;

import java.util.List;

public interface TagService {
    int getTotalTags();

    PageResult getBlogTagPage(PageQueryUtil pageQueryUtil);

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] ids);

    List<BlogTagCount> getBlogTagCountForIndex();

}
