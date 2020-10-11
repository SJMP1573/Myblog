package com.sjmp.service;

import com.sjmp.po.Comment;

import java.util.List;

/**
 * @author: sjmp1573
 * @date: 2020/10/4 15:25
 * @description:
 */

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
