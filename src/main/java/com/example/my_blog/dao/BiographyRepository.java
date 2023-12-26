package com.example.my_blog.dao;

import com.example.my_blog.entity.Biography;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiographyRepository extends JpaRepository<Biography, Long> {
}
