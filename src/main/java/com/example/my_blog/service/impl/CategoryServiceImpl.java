package com.example.my_blog.service.impl;

import com.example.my_blog.dao.CategoryRepository;
import com.example.my_blog.entity.Type;
import com.example.my_blog.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Type saveCategory(Type type) {
        return categoryRepository.save(type);
    }

    @Transactional
    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Type queryCategoryById(Long typeId) {
        return categoryRepository.findById(typeId).orElse(null);
    }

    @Override
    public Page<Type> queryCategory(Pageable pageableType) {
        return categoryRepository.findAll(pageableType);
    }

    @Override
    public long countCategory(Type type) {
        return categoryRepository.count(Example.of(type));
    }

    @Override
    public List<Type> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Type> queryTop(Integer top) {
        Pageable pageable = PageRequest.of(0, top);
        return categoryRepository.findTop(pageable);
    }
}
