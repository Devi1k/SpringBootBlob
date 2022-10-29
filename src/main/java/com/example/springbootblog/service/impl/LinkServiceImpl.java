package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogLinkMapper;
import com.example.springbootblog.entity.BlogLink;
import com.example.springbootblog.service.LinkService;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    @Resource
    private BlogLinkMapper linkMapper;

    @Override
    public int getTotalLinks() {
        return 0;
    }

    @Override
    public PageResult getBlogLinkPage(PageQueryUtil pageQueryUtil) {
        List<BlogLink> linkList = linkMapper.findLinkList(pageQueryUtil);
        int total = linkMapper.getTotalLinks(pageQueryUtil);
        PageResult pageResult = new PageResult(linkList, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1)
            return false;
        return linkMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Boolean saveLink(BlogLink link) {
        return linkMapper.insertSelective(link) > 0;
    }

    @Override
    public Boolean updateLink(BlogLink link) {
        return linkMapper.updateByPrimaryKeySelective(link) > 0;
    }

    @Override
    public BlogLink selectById(Integer linkId) {
        return linkMapper.selectByPrimaryKey(linkId);
    }
}
