package com.example.my_blog.dao;

import com.example.my_blog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlogBlogId(Long blogId, Sort sort);

    void deleteAllByBlogBlogId(Long blogId);
}
