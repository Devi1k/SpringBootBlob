package com.example.springbootblog.dao;

import com.example.springbootblog.entity.BlogCategory;
import com.example.springbootblog.utils.PageQueryUtil;

import java.util.List;

public interface BlogCategoryMapper {

    int getTotalCategories(PageQueryUtil pageQueryUtil);

    List<BlogCategory> findCategoryList(PageQueryUtil pageQueryUtil);

    BlogCategory selectByCategoryName(String categoryName);

    int insertSelective(BlogCategory blogCategory);

    int deleteBatch(Integer[] ids);

    BlogCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogCategory record);
}
