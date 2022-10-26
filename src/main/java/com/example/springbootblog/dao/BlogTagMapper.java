package com.example.springbootblog.dao;

import com.example.springbootblog.entity.BlogTag;
import com.example.springbootblog.entity.BlogTagCount;
import com.example.springbootblog.utils.PageQueryUtil;

import java.util.List;

public interface BlogTagMapper {
    int getTotalTags(PageQueryUtil pageUtil);

    List<BlogTag> findTagList(PageQueryUtil pageQueryUtil);

    BlogTag selectByTagName(String tagName);

    int insertSelective(BlogTag blogTag);

    int deleteBatch(Integer[] ids);

    List<BlogTagCount> getTagCount();


    int batchInsertBlogTag(List<BlogTag> list);
}
