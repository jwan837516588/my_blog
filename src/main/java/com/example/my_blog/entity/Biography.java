package com.example.my_blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="t_biography")
public class Biography {
    @Id
    @GeneratedValue
    private Long id;

    private boolean publish;

    @NotBlank(message = "Introduction cannot be empty")
    private String intro;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="TEXT")
    @NotBlank(message = "Content cannot be empty.")
    private String content;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
