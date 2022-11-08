package com.example.springbootblog.dao;

import com.example.springbootblog.entity.BlogComment;
import com.example.springbootblog.utils.PageQueryUtil;

import java.util.List;
import java.util.Map;

public interface BlogCommentMapper {
    int getTotalBlogComments(Map map);

    List<BlogComment> findBlogCommentList(PageQueryUtil pageQueryUtil);

    int checkDone(Integer[] ids);

    BlogComment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(BlogComment blogComment);

    int deleteBatch(Integer[] ids);

    int insertSelective(BlogComment comment);
}
