package com.example.springbootblog.service;

import com.example.springbootblog.entity.BlogLink;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;

public interface LinkService {
    int getTotalLinks();

    PageResult getBlogLinkPage(PageQueryUtil pageQueryUtil);

    Boolean deleteBatch(Integer[] ids);

    Boolean saveLink(BlogLink link);

    Boolean updateLink(BlogLink link);

    BlogLink selectById(Integer linkId);
}
