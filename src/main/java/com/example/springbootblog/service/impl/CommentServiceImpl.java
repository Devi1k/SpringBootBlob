package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.BlogCommentMapper;
import com.example.springbootblog.entity.BlogComment;
import com.example.springbootblog.service.CommentService;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private BlogCommentMapper commentMapper;

    @Override
    public int getTotalComments() {
        return commentMapper.getTotalBlogComments(null);
    }

    @Override
    public PageResult getCommentsPage(PageQueryUtil pageQueryUtil) {
        List<BlogComment> comments = commentMapper.findBlogCommentList(pageQueryUtil);
        int total = commentMapper.getTotalBlogComments(pageQueryUtil);
        PageResult pageResult = new PageResult(comments, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean checkDone(Integer[] ids) {
        return commentMapper.checkDone(ids) > 0;
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
        BlogComment blogComment = commentMapper.selectByPrimaryKey(commentId);
        if (blogComment != null && blogComment.getCommentStatus().intValue() == 1) {
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(new Date());
            return commentMapper.updateByPrimaryKeySelective(blogComment) > 0;
        }
        return false;
    }

    @Override
    public Boolean delete(Integer[] ids) {
        return commentMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Boolean addComment(BlogComment comment) {
        return commentMapper.insertSelective(comment) > 0;
    }

    @Override
    public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page) {
        if (page < 1) {
            return null;
        }
        Map params = new HashMap();
        params.put("page", page);
        params.put("limit", 3);
        params.put("blogId", blogId);
        params.put("commentStatus", 1);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        List<BlogComment> comments = commentMapper.findBlogCommentList(pageQueryUtil);
        if (!CollectionUtils.isEmpty(comments)) {
            int total = commentMapper.getTotalBlogComments(pageQueryUtil);
            return new PageResult(comments, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        }
        return null;
    }


}
