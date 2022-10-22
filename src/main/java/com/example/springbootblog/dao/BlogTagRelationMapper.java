package com.example.springbootblog.dao;

import java.util.List;

public interface BlogTagRelationMapper {
    List<Long> selectDistinctTagIds(Integer[] tagIds);
}
