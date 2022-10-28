package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogCategoryMapper;
import com.example.springbootblog.dao.BlogMapper;
import com.example.springbootblog.dao.BlogTagMapper;
import com.example.springbootblog.dao.BlogTagRelationMapper;
import com.example.springbootblog.entity.Blog;
import com.example.springbootblog.entity.BlogCategory;
import com.example.springbootblog.entity.BlogTag;
import com.example.springbootblog.entity.BlogTagRelation;
import com.example.springbootblog.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogMapper blogMapper;

    @Resource
    private BlogCategoryMapper categoryMapper;

    @Resource
    private BlogTagMapper blogTagMapper;

    @Resource
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public int getTotalBlogs() {
        return blogMapper.getTotalBlogs(null);
    }

    @Override
    @Transactional
    public String saveBlog(Blog blog) {
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blogCategory.setCategoryName("默认分类");
        } else {
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        categoryMapper.updateByPrimaryKeySelective(blogCategory);
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量最大为6";
        }
        if (blogMapper.insertSelective(blog) > 0) {
            List<BlogTag> tagListForInsert = new ArrayList<>();
            List<BlogTag> allTagList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                BlogTag tag = blogTagMapper.selectByTagName(tags[i]);
                if (tag == null) {
                    BlogTag temp = new BlogTag();
                    temp.setTagName(tags[i]);
                    tagListForInsert.add(temp);
                } else {
                    allTagList.add(tag);
                }
            }
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                blogTagMapper.batchInsertBlogTag(tagListForInsert);
            }
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            allTagList.addAll(tagListForInsert);
            for (BlogTag blogTag : allTagList
            ) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(blogTag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                return "success";
            }
        }
        return "保存失败";
    }

    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if (blogForUpdate == null) {
            return "数据不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());

        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }

        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());

        List<BlogTag> tagForForInsert = new ArrayList<>();
        List<BlogTag> allTagsList = new ArrayList<>();

        for (String s : tags) {
            BlogTag tag = blogTagMapper.selectByTagName(s);
            if (tag == null) {
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(s);
                tagForForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }

        if (!CollectionUtils.isEmpty(tagForForInsert)) {
            blogTagMapper.batchInsertBlogTag(tagForForInsert);
        }
        allTagsList.addAll(tagForForInsert);

        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        for (BlogTag tag :
                allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelations.add(blogTagRelation);
        }


        categoryMapper.updateByPrimaryKeySelective(blogCategory);


        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            return "success";
        }
        return "修改失败";
    }

    @Override
    public Blog getBlogById(Long blogId) {
        return null;
    }
}
