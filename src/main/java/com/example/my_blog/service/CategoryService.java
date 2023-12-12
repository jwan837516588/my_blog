package com.example.my_blog.service;

import com.example.my_blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    void saveCategory(Type type);
    void deleteCategory(Type type);
    Type queryCategory(Type type);
    Page<Type> queryCategory(Pageable pageableType);
    void updateCategory(Type type);
}
