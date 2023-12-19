package com.example.my_blog.service;

import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.scope.BlogScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogService {
    Blog saveBlog(Blog blog);

    @Transactional
    void deleteBlogById(Long id);

    Blog queryBlogs(Blog blog);
    Blog queryBlogById(Long blogId);
    Page<Blog> queryBlogs(Pageable pageableBlog, BlogScope blog);
//    void updateBlog(blog blog);

    long countBlog(Blog blog);

    Page<Blog> queryBlogs(Pageable pageable);

    List<Blog> queryTopRecommend(Integer top);

    Page<Blog> queryBlogs(String query, Pageable pageable);

    Blog getConvertedBlogById(Long id);
}
