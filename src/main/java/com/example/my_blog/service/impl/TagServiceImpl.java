package com.example.my_blog.service.impl;

import com.example.my_blog.dao.TagRepository;
import com.example.my_blog.entity.Tag;
import com.example.my_blog.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void deleteTagById(Long id) {
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
        return tagRepository.findTop(pageable);
    }
}
