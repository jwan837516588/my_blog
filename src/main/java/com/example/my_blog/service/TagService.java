package com.example.my_blog.service;

import com.example.my_blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    @Transactional
    void deleteTagById(Long id);

    Tag queryTag(Tag tag);
    Tag queryTagById(Long tagId);
    List<Tag> queryBatchTagsByIds(List<Long> tagIds);
    Page<Tag> queryTag(Pageable pageableTag);

    long countTag(Tag tag);

    Tag queryTagByName(String name);

    List<Tag> findAllTags();

    List<Tag> queryTop(Integer top);

    List<Tag> queryAllTags();
}
