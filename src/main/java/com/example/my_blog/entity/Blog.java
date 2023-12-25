package com.example.my_blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long blogId;
    @NotBlank(message = "Title cannot be empty.")
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="TEXT")
    @NotBlank(message = "Content cannot be empty.")
    private String content;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] profile;
    @NotBlank(message = "Flag cannot be empty.")
    private String flag;
    private Integer numOfViews;
    private boolean donationSwitch;
    private boolean copyrightSwitch;
    private boolean commentSwitch;
    private boolean publish;
    private boolean recommendSwitch;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @NotBlank(message = "Description cannot be empty.")
    private String description;

    @Transient
    private MultipartFile file;

    @ManyToOne
    private Type type;

    @ManyToOne
    private User user;

    @Transient
    private String tagIds;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();


    public Blog() {

    }

    public Blog(Long blogId) {
        this.blogId = blogId;
    }

    public Blog(Long blogId, String title, String content, byte[] profile, String flag, Integer numOfViews, boolean donationSwitch, boolean copyrightSwitch, boolean commentSwitch, boolean publish, boolean recommendSwitch, Date createTime, Date updateTime, String description, Type type, User user, String tagIds, List<Tag> tags, List<Comment> comments) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.profile = profile;
        this.flag = flag;
        this.numOfViews = numOfViews;
        this.donationSwitch = donationSwitch;
        this.copyrightSwitch = copyrightSwitch;
        this.commentSwitch = commentSwitch;
        this.publish = publish;
        this.recommendSwitch = recommendSwitch;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.description = description;
        this.type = type;
        this.user = user;
        this.tagIds = tagIds;
        this.tags = tags;
        this.comments = comments;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(Integer numOfViews) {
        this.numOfViews = numOfViews;
    }

    public boolean isDonationSwitch() {
        return donationSwitch;
    }

    public void setDonationSwitch(boolean donationSwitch) {
        this.donationSwitch = donationSwitch;
    }

    public boolean isCopyrightSwitch() {
        return copyrightSwitch;
    }

    public void setCopyrightSwitch(boolean copyrightSwitch) {
        this.copyrightSwitch = copyrightSwitch;
    }

    public boolean isCommentSwitch() {
        return commentSwitch;
    }

    public void setCommentSwitch(boolean commentSwitch) {
        this.commentSwitch = commentSwitch;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public boolean isRecommendSwitch() {
        return recommendSwitch;
    }

    public void setRecommendSwitch(boolean recommendSwitch) {
        this.recommendSwitch = recommendSwitch;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", profile='" + profile + '\'' +
                ", flag='" + flag + '\'' +
                ", numOfViews=" + numOfViews +
                ", donationSwitch=" + donationSwitch +
                ", copyrightSwitch=" + copyrightSwitch +
                ", commentSwitch=" + commentSwitch +
                ", publish=" + publish +
                ", recommendSwitch=" + recommendSwitch +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", user=" + user +
                ", tagIds='" + tagIds + '\'' +
                ", tags=" + tags +
                ", comments=" + comments +
                '}';
    }
}
