package com.example.my_blog.service.impl;

import com.example.my_blog.dao.BlogRepository;
import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.Type;
import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogRepository blogRepository;
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog queryBlog(Blog blog) {
        return blogRepository.findOne(Example.of(blog)).orElse(null);
    }

    @Override
    public Blog queryBlogById(Long blogId) {
        return blogRepository.findById(blogId).orElse(null);
    }

    @Override
    public Page<Blog> queryBlog(Pageable pageableBlog, BlogScope blog) {
        Specification<Blog> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle() + "%"));
            }
            if (blog.getTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.<Type>get("type").<Long>get("typeId"), blog.getTypeId()));
            }
            if (blog.isRecommend() != null && blog.isRecommend()) {
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommendSwitch"), blog.isRecommend()));
            }
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        };
        return blogRepository.findAll(spec, pageableBlog);
    }

    @Override
    public long countBlog(Blog blog) {
        return blogRepository.count(Example.of(blog));
    }

}
