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
import com.example.springbootblog.utils.MarkDownUtil;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;
import com.example.springbootblog.utils.PatternUtil;
import com.example.springbootblog.vo.BlogDetailVO;
import com.example.springbootblog.vo.BlogListVO;
import com.example.springbootblog.vo.SimpleBlogListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
            System.out.println("1111111111");

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
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public PageResult getBlogsPage(PageQueryUtil pageQueryUtil) {
        List<Blog> blogList = blogMapper.findBlogList(pageQueryUtil);
        int total = blogMapper.getTotalBlogs(pageQueryUtil);
        PageResult pageResult = new PageResult(blogList, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        return blogMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        List<Blog> blogList = blogMapper.findBlogListByType(type, 9);
        if (!CollectionUtils.isEmpty(blogList)) {
            for (Blog blog : blogList) {
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                BeanUtils.copyProperties(blog, simpleBlogListVO);
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }


    public PageResult getBlogsForIndexPage(int page) {
        Map params = new HashMap();
        params.put("page", page);
        params.put("limit", 8);
        params.put("blogStatus", 1);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        List<Blog> blogList = blogMapper.findBlogList(pageQueryUtil);
        int total = blogMapper.getTotalBlogs(pageQueryUtil);
        PageResult pageResult = new PageResult(blogList, total, pageQueryUtil.getLimit(), page);
        return pageResult;
    }

    @Override
    public PageResult getBlogsPageForSearch(String keyword, int page) {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            Map params = new HashMap();
            params.put("page", page);
            params.put("limit", 9);
            params.put("keyword", keyword);
            params.put("blogStatus", 1);
            PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
            List<Blog> blogList = blogMapper.findBlogList(pageQueryUtil);
            List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
            int total = blogMapper.getTotalBlogs(pageQueryUtil);
            PageResult pageResult = new PageResult(blogListVOS, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
            return pageResult;
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
            BlogCategory blogCategory = categoryMapper.selectByCategoryName(categoryName);
            if ("默认分类".equals(categoryName) && blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
            }
            if (blogCategory != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("blogCategoryId", blogCategory.getCategoryId());
                param.put("blogStatus", 1);
                PageQueryUtil pageUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.findBlogList(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogs(pageUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        if (PatternUtil.validKeyword(tagName)) {
            BlogTag tag = blogTagMapper.selectByTagName(tagName);
            if (tag != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("tagId", tag.getTagId());
                PageQueryUtil pageUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogsByTagId(pageUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }


    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
                List<BlogCategory> blogCategories = categoryMapper.selectByCategoryIds(categoryIds);
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));

                }
            }
            for (Blog blog : blogList
            ) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/1.png");
                }
                blogListVOS.add(blogListVO);

            }
        }
        return blogListVOS;
    }


    public BlogDetailVO getBlogDetail(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;
    }


    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null && blog.getBlogStatus() != 0) {
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKeySelective(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (!StringUtils.isEmpty(blog.getBlogTags())) {
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            return blogDetailVO;
        }
        return null;
    }
}
