package com.example.my_blog.service.impl;

import com.example.my_blog.dao.CategoryRepository;
import com.example.my_blog.entity.Type;
import com.example.my_blog.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void saveCategory(Type type) {
        categoryRepository.save(type);
    }

    @Transactional
    @Override
    public void deleteCategory(Type type) {
        categoryRepository.delete(type);
    }

    @Override
    public Type queryCategory(Type type) {
        Optional<Type> category = categoryRepository.findOne(Example.of(type));
        return category.orElse(null);
    }

    @Override
    public Page<Type> queryCategory(Pageable pageableType) {
        return categoryRepository.findAll(pageableType);
    }

    @Transactional
    @Override
    public void updateCategory(Type type) {
        categoryRepository.findOne(Example.of(type)).ifPresent(e -> e.setName(type.getName()));
    }
}
