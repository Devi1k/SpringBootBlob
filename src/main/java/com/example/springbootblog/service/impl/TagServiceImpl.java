package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogTagMapper;
import com.example.springbootblog.dao.BlogTagRelationMapper;
import com.example.springbootblog.entity.BlogTag;
import com.example.springbootblog.entity.BlogTagCount;
import com.example.springbootblog.service.TagService;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private BlogTagMapper blogTagMapper;
    @Resource
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public int getTotalTags() {
        return 0;
    }

    @Override
    public PageResult getBlogTagPage(PageQueryUtil pageQueryUtil) {
        List<BlogTag> list = blogTagMapper.findTagList(pageQueryUtil);
        int total = blogTagMapper.getTotalTags(pageQueryUtil);
        PageResult pageResult = new PageResult(list, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean saveTag(String tagName) {
        BlogTag temp = blogTagMapper.selectByTagName(tagName);
        if (temp == null) {
            BlogTag blogTag = new BlogTag();
            blogTag.setTagName(tagName);
            return blogTagMapper.insertSelective(blogTag) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        List<Long> relations = blogTagRelationMapper.selectDistinctTagIds(ids);
        if (!CollectionUtils.isEmpty(relations)) {
            return false;
        }
        return blogTagMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return blogTagMapper.getTagCount();
    }


}
