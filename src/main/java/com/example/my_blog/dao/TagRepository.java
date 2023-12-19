package com.example.my_blog.dao;

import com.example.my_blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    @Query("select t from Tag t join BlogTags bt on t.tagId=bt.tagId group by t.tagId order by count(bt.blogId) DESC")
    List<Tag> findTop(Pageable pageable);
}
