package com.example.my_blog.dao;

import com.example.my_blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommendSwitch=true and b.publish")
    List<Blog> queryTopRecommend(Pageable pageable);
    @Query("select b from Blog b where b.publish and (b.title like ?1 or b.content like ?1)")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.numOfViews = b.numOfViews+1 where b.blogId=?1")
    void updateViews(Long id);

    @Query("select function('date_format', b.updateTime, '%Y') as y from Blog b where b.publish group by y order by function('date_format', b.updateTime, '%Y') desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

    Page<Blog> findAllByPublishIsTrue(Pageable pageable);

    long countBlogsByTypeTypeId(Long id);

    long countBlogByPublishIsTrue();
}
