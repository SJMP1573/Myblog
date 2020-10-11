package com.sjmp.po;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author SJMP1573
 * @Date 2020/9/28 20:11
 * @Description
 * @Since version-1.0
 */

@Entity  // 生成对应的数据库
@Table( name = "t_tag") // 指定表的名字
public class Tag {

    @Id // 指定主键
    @GeneratedValue // 自动生成类
    private Long id;
    @NotBlank(message = "标签名称不能为空")//与 TypeController 中 @Valid 一同使用，后端校验数据库数据为空吗？（新增 Tag 为空？）
    private String name;
    @ManyToMany(mappedBy = "tags")// tags 被维护 Blog 与 Tag 的关系
    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
