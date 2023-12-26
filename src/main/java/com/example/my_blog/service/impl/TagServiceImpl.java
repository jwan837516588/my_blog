package com.example.my_blog.service.impl;

import com.example.my_blog.dao.TagRepository;
import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.Tag;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.TagService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagRepository tagRepository;

    @Resource
    private BlogService blogService;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void deleteTagById(Long id) {
        // disassociate blogs and tags
        List<Blog> blogs = blogService.queryBlogsByTagId(id);
        blogs.forEach(e->e.setTags(null));
        blogService.saveBatchBlogs(blogs);
        // delete tag
        tagRepository.deleteById(id);
    }

    @Override
    public Tag queryTag(Tag tag) {
        Optional<Tag> category = tagRepository.findOne(Example.of(tag));
        return category.orElse(null);
    }

    @Override
    public Tag queryTagById(Long tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    @Override
    public List<Tag> queryBatchTagsByIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    @Override
    public Page<Tag> queryTag(Pageable pageableTag) {
        return tagRepository.findAll(pageableTag);
    }

    @Override
    public long countTag(Tag tag) {
        return tagRepository.count(Example.of(tag));
    }

    @Override
    public Tag queryTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> queryTop(Integer top) {
        Pageable pageable = PageRequest.of(0, top);
        Specification<Tag> spec = (root, query, criteriaBuilder) -> {
            Join<Tag, Blog> join = root.join("blogs", JoinType.LEFT);
            Predicate publishCondition = criteriaBuilder.equal(join.get("publish"), true);
            query.where(publishCondition);
            query.groupBy(root.get("tagId"));
            query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(join.get("blogId"))));

            return null;
        };
        List<Tag> content = new ArrayList<>(tagRepository.findAll(spec, pageable).getContent());
        // Only include tags with associated blogs
        content.removeIf(e -> CollectionUtils.isEmpty(e.getBlogs()));
        return content;
    }

    @Override
    public List<Tag> queryAllTags() {
        return tagRepository.findAll();
    }
}
