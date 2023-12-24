package com.example.my_blog.service;

import com.example.my_blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    Type saveCategory(Type type);

    @Transactional
    void deleteCategoryById(Long id);

    Type queryCategoryById(Long typeId);
    Page<Type> queryCategory(Pageable pageableType);

    long countCategory(Type type);

    List<Type> findAllCategories();

    List<Type> queryTop(Integer top);
}
