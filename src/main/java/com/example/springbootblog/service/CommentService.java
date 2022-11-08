package com.example.springbootblog.service;

import com.example.springbootblog.entity.BlogComment;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.PageResult;

public interface CommentService {
    int getTotalComments();

    PageResult getCommentsPage(PageQueryUtil pageQueryUtil);

    Boolean checkDone(Integer[] ids);

    Boolean reply(Long commentId, String replyBody);

    Boolean delete(Integer[] ids);

    Boolean addComment(BlogComment comment);

    PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page);
}
