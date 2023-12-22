package com.example.my_blog.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private long commentId;
    private String nickname;
    private String photo;
    private String email;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Blog blog;

    // 当前Comment类作为one时，reply comments是many，
    @OneToMany(mappedBy = "pComment", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    // 当前comment为reply comment是多，则对应上级comment为1
    @ManyToOne()
    private Comment pComment;

    private boolean adminComment;

    public Comment(long commentId, String nickname, String photo, String email, String content, Date createTime) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.photo = photo;
        this.email = email;
        this.content = content;
        this.createTime = createTime;
    }

    public Comment() {
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Comment getpComment() {
        return pComment;
    }

    public void setpComment(Comment pComment) {
        this.pComment = pComment;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", nickname='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
