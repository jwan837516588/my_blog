package com.example.my_blog.dao;

import com.example.my_blog.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Type, Long> {
}
