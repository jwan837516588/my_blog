package com.example.my_blog.dao;

import com.example.my_blog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("select t from Type t join Blog b on t.typeId=b.type.typeId where b.publish order by b.blogId desc")
    List<Type> findTop(Pageable pageable);
}
