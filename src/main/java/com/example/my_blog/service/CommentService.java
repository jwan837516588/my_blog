package com.example.my_blog.service;

import com.example.my_blog.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> queryCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
