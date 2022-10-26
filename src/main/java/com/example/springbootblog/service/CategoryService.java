package com.example.springbootblog.service;

import com.example.springbootblog.entity.BlogCategory;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;

import java.util.List;

public interface CategoryService {
    int getTotalCategories();

    public PageResult getBlogCategoryPage(PageQueryUtil pageQueryUtil);

    public Boolean saveCategory(String categoryName, String categoryIcon);

    public Boolean deleteBatch(Integer[] ids);

    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon);

    public List<BlogCategory> getAllCategories();
}
