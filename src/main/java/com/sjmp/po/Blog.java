package com.sjmp.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author SJMP1573
 * @Date 2020/9/28 20:02
 * @Description blog
 * @Since version-1.0
 */

// @Entity 对应到数据库生成指定的表
@Entity
// 对应数据库的表名字("t_blog"),统一前缀
@Table(name = "t_blog")
public class Blog {

    @Id // 标记主键
    @GeneratedValue // 自动生成
    // 博客文章的 id，Long包装类。
    private Long id;
    // 博客文章的 标题。
    private String title;
//    博客文章的内容。
    @Basic(fetch = FetchType.LAZY) //这两个的功能是将 content 标记为 Longtext 字段，否则大小为255
    @Lob
    private String content;
    // 博客 的插图
    private String firstPicture;
    // 博客是 转载/原创
    private String flag;
    // 浏览的数量
    private Integer views;
    // 是否开启赞赏
    private boolean appreciation;
    // 是否开启分享声明
    private boolean shareStatement;
    // 是否开启评论
    private boolean commentabled;
    // 保存草稿还是发布
    private boolean published;
    // 是否标记为推荐
    private boolean recommend;
    // 创建的时间
    @Temporal(TemporalType.TIMESTAMP) // 对应的数据库的表时间
    private Date createTime;
    // 修改的时间
    @Temporal(TemporalType.TIMESTAMP) // 对应的数据库的表时间
    private Date updateTime;
    @ManyToOne // Blog 主动维护与 Type 的关系，包含一个Type
    private Type type;
    // CascadeType.PERSIST ,级联新增，增加一个新标签会自动保存。
    @ManyToMany(cascade = {CascadeType.PERSIST}) // 多对多的关系
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
    @Transient
    private String tagIds;
    private String description;

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    // 将当前的tags 的数组，转换成用逗号分割的字符串
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public String    toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
