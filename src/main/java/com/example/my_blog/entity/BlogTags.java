package com.example.my_blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name="t_blog_tags")
public class BlogTags {
    @Id
    @GeneratedValue
    private Long blogTagId;

    @Column(name = "blogs_blog_id")
    private Long blogId;
    @Column(name = "tags_tag_id")
    private Long tagId;

    public Long getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(Long blogTagId) {
        this.blogTagId = blogTagId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
