package com.example.springbootblog.dao;

import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    int getTotalBlogs(PageQueryUtil pageUtil);

    int updateBlogCategories(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);


    Blog selectByPrimaryKey(Long blogId);

    int insertSelective(Blog record);

    int updateByPrimaryKeySelective(Blog blog);

    List<Blog> findBlogList(PageQueryUtil pageQueryUtil);

    int deleteBatch(Integer[] ids);

    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);

    List<Blog> getBlogsPageByTagId(PageQueryUtil pageQueryUtil);

    int getTotalBlogsByTagId(PageQueryUtil pageQueryUtil);
}
