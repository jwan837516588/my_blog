package com.example.my_blog.service;

import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.scope.BlogScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog saveBlog(Blog blog);

    void deleteBlogById(Long id);

    Blog queryBlogs(Blog blog);
    Blog queryBlogById(Long blogId);
    Page<Blog> queryBlogs(Pageable pageableBlog, BlogScope blog);

    Page<Blog> queryBlogs(Pageable pageable);

    List<Blog> queryTopBlogs(int type, Integer top);

    Page<Blog> queryBlogs(String query, Pageable pageable);

    Blog getConvertedBlogById(Long id);

    Page<Blog> queryBlogsByTagId(Pageable pageable, Long tagId);
    List<Blog> queryBlogsByTagId(Long tagId);

    Map<String, List<Blog>> queryBlogsByYear();

    int countBlogs();

    void saveBatchBlogs(List<Blog> blogs);

    long countBlogsByTypeId(Long id);
}
