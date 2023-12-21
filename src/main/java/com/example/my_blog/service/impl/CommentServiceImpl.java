package com.example.my_blog.service.impl;

import com.example.my_blog.dao.CommentRepository;
import com.example.my_blog.entity.Comment;
import com.example.my_blog.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentRepository commentRepository;

    private List<Comment> tempComments = new ArrayList<>();

    @Override
    public List<Comment> queryCommentByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findAllByBlogIdAndPCommentNull(blogId, Sort.by("createTime"));
        return iterHeadNode(comments);
    }

    private List<Comment> iterHeadNode(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment target = new Comment();
            BeanUtils.copyProperties(comment, target);
            commentsView.add(target);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replies = comment.getComments();
            for (Comment reply : replies) {
                doRecursion(reply);
            }
            comment.setComments(tempComments);
            tempComments = new ArrayList<>();
        }
    }

    private void doRecursion(Comment reply) {
        tempComments.add(reply);
        List<Comment> comments = reply.getComments();
        for (Comment comment : comments) {
            tempComments.add(comment);
            if (!comment.getComments().isEmpty()) {
                doRecursion(comment);
            }
        }
    }

    @Override
    public Comment saveComment(Comment comment) {
        // default value -1 is assigned from the front-end
        long parentCommentId = comment.getpComment().getCommentId();
        // find parent comment, if id exist, then set found one to pComment, otherwise set null.
        Comment pComment = commentRepository.findById(parentCommentId).orElse(null);
        comment.setpComment(pComment);

        comment.setCreateTime(new Date());

        return commentRepository.save(comment);
    }
}
