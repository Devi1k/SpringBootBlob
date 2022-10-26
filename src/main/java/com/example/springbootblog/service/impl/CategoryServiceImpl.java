package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogCategoryMapper;
import com.example.springbootblog.dao.BlogMapper;
import com.example.springbootblog.entity.BlogCategory;
import com.example.springbootblog.service.CategoryService;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private BlogCategoryMapper blogCategoryMapper;
    @Resource
    private BlogMapper blogMapper;

    @Override
    public int getTotalCategories() {
        return 0;
    }

    @Override
    public PageResult getBlogCategoryPage(PageQueryUtil pageQueryUtil) {
        List<BlogCategory> categoryList = blogCategoryMapper.findCategoryList(pageQueryUtil);
        int total = blogCategoryMapper.getTotalCategories(pageQueryUtil);
        return new PageResult(categoryList, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
    }

    @Override
    public Boolean saveCategory(String categoryName, String categoryIcon) {
        BlogCategory temp = blogCategoryMapper.selectByCategoryName(categoryName);
        if (temp == null) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            return blogCategoryMapper.insertSelective(blogCategory) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        blogMapper.updateBlogCategories("默认分类", 0, ids);
        return blogCategoryMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(categoryId);
        if (blogCategory != null) {
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            blogMapper.updateBlogCategories(categoryName, blogCategory.getCategoryId(), new Integer[]{categoryId});
            return blogCategoryMapper.updateByPrimaryKeySelective(blogCategory) > 0;
        }
        return false;
    }

    @Override
    public List<BlogCategory> getAllCategories() {
        return blogCategoryMapper.findCategoryList(null);
    }


}
