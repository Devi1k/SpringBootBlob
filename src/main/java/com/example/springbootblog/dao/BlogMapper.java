package com.example.springbootblog.dao;

import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogMapper {
    int getTotalBlogs(PageQueryUtil pageUtil);

    int updateBlogCategories(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);


    Blog selectByPrimaryKey(Long blogId);

    int insertSelective(Blog record);

    int updateByPrimaryKeySelective(Long blogId);

}
