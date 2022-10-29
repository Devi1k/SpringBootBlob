package com.example.springbootblog.dao;

import com.example.springbootblog.entity.BlogLink;
import com.example.springbootblog.utils.PageQueryUtil;

import java.util.List;

public interface BlogLinkMapper {
    int getTotalLinks(PageQueryUtil pageUtil);

    List<BlogLink> findLinkList(PageQueryUtil pageQueryUtil);

    BlogLink selectByPrimaryKey(Integer linkId);

    int deleteByPrimaryKey(Integer linkId);

    int deleteBatch(Integer[] ids);

    int insertSelective(BlogLink blogLink);


    int updateByPrimaryKeySelective(BlogLink blogLink);

}
