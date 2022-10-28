package com.example.springbootblog.dao;

import com.example.springbootblog.entity.BlogTagRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTagRelationMapper {
    List<Long> selectDistinctTagIds(Integer[] tagIds);

    int batchInsert(@Param("relationList") List<BlogTagRelation> list);


    int deleteByBlogId(Long blogId);
}
