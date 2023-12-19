package com.example.my_blog.service.impl;

import com.example.my_blog.coustom_exception.NotFoundException;
import com.example.my_blog.dao.BlogRepository;
import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.Type;
import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.utils.MarkdownUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        if (blog.getBlogId() == null) {
            blog.setCreateTime(new Date());
            blog.setNumOfViews(0);
        } else {
            Blog b = queryBlogById(blog.getBlogId());
            blog.setNumOfViews(b.getNumOfViews());
            blog.setCreateTime(b.getCreateTime());
        }
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Override
    @Transactional
    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog queryBlogs(Blog blog) {
        return blogRepository.findOne(Example.of(blog)).orElse(null);
    }

    @Override
    public Blog queryBlogById(Long blogId) {
        return blogRepository.findById(blogId).orElse(null);
    }

    @Override
    public Page<Blog> queryBlogs(Pageable pageableBlog, BlogScope blog) {
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

    @Override
    public Page<Blog> queryBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> queryTopRecommend(Integer top) {
        Pageable pageable = PageRequest.of(0, top,
                Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.queryTopRecommend(pageable);
    }

    @Override
    public Page<Blog> queryBlogs(String query, Pageable pageable) {
        query = "%" + query + "%";
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Blog getConvertedBlogById(Long id) {
        Blog blog = queryBlogById(id);
        if (blog == null) {
            throw new NotFoundException("The blog does not exist");
        }
        Blog res = new Blog();
        BeanUtils.copyProperties(blog, res);
        res.setContent(MarkdownUtils.markdownToHtmlExtension(res.getContent()));
        return res;
    }

}
