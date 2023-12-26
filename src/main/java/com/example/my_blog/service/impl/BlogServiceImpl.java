package com.example.my_blog.service.impl;

import com.example.my_blog.enums.LatestOrRecommendEnum;
import com.example.my_blog.coustom_exception.NotFoundException;
import com.example.my_blog.dao.BlogRepository;
import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.Tag;
import com.example.my_blog.entity.Type;
import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CommentService;
import com.example.my_blog.utils.MarkdownUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogRepository blogRepository;
    @Resource
    private CommentService commentService;

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
        commentService.deleteCommentsByBlogId(id);
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
    public Page<Blog> queryBlogs(Pageable pageable) {
        return blogRepository.findAllByPublishIsTrue(pageable);
    }

    @Override
    public Page<Blog> queryBlogs(String query, Pageable pageable) {
        query = "%" + query + "%";
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> queryTopBlogs(int type, Integer top) {
        Pageable pageable = PageRequest.of(0, top,
                Sort.by(Sort.Direction.DESC, "updateTime"));
        return type == LatestOrRecommendEnum.Latest.getType() ?
                blogRepository.queryLatestPublished(pageable) :
                blogRepository.queryTopRecommend(pageable);
    }

    @Override
    @Transactional
    public Blog getConvertedBlogById(Long id) {
        Blog blog = queryBlogById(id);
        if (blog == null) {
            throw new NotFoundException("The blog does not exist");
        }
        Blog res = new Blog();
        BeanUtils.copyProperties(blog, res);
        res.setContent(MarkdownUtils.markdownToHtmlExtension(res.getContent()));

        blogRepository.updateViews(id);

        return res;
    }

    @Override
    public Page<Blog> queryBlogsByTagId(Pageable pageable, Long tagId) {
        Specification<Blog> spec = (root, query, criteriaBuilder) -> {
            Join<Blog, Tag> tags = root.join("tags");
            return criteriaBuilder.equal(tags.get("tagId"), tagId);
        };
        return blogRepository.findAll(spec, pageable);
    }

    @Override
    public List<Blog> queryBlogsByTagId(Long tagId) {
        Specification<Blog> spec = (root, query, criteriaBuilder) -> {
            Join<Blog, Tag> tags = root.join("tags");
            return criteriaBuilder.equal(tags.get("tagId"), tagId);
        };
        return blogRepository.findAll(spec);
    }

    @Override
    public Map<String, List<Blog>> queryBlogsByYear() {
        HashMap<String, List<Blog>> blogsGroupByYear = new HashMap<>();
        List<String> years = blogRepository.findGroupYear();
        for (String year : years) {
            blogsGroupByYear.put(year, blogRepository.findByYear(year));
        }
        return blogsGroupByYear;
    }

    @Override
    public int countBlogs() {
        return (int) blogRepository.countBlogByPublishIsTrue();
    }

    @Override
    @Transactional
    public void saveBatchBlogs(List<Blog> blogs) {
        blogRepository.saveAll(blogs);
    }

    @Override
    public long countBlogsByTypeId(Long id) {
        return blogRepository.countBlogsByTypeTypeId(id);
    }

}
