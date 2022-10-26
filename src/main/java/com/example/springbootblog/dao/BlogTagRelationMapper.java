package com.example.springbootblog.dao;

import com.example.springbootblog.entity.BlogTagRelation;

import java.util.List;

public interface BlogTagRelationMapper {
    List<Long> selectDistinctTagIds(Integer[] tagIds);

    int batchInsert(List<BlogTagRelation> list);
}
