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
    @GeneratedValue(strategy = GenerationType.AUTO) // AUTO 主键由程序控制，指定增量的方式
    private Long id;
    @NotBlank(message = "标签名称不能为空") //与 TypeController 中 @Valid 一同使用，后端校验数据库数据为空吗？（新增 Tag 为空？）
    private String name;
    @ManyToMany(mappedBy = "tags") // blogs 是关系维护方（一方有建立、解出和更新另一方的能力），blog 的删除不会影响 tag
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
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
