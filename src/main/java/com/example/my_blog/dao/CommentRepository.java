package com.example.my_blog.dao;

import com.example.my_blog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlogIdAndPCommentNull(Long blogId, Sort sort);
}
