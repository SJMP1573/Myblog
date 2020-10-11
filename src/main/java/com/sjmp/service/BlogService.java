package com.sjmp.service;

import com.sjmp.po.Blog;
import com.sjmp.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author: sjmp1573
 * @date: 2020/10/2 11:30
 * @description:
 */

public interface BlogService {
//    通过 id 获取 Blog
    Blog getBlog(Long id);
    Blog getAndConvert(Long id);
//    通过 Blog 的一些信息查询并返回 Blog 列表
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    Page<Blog> listBlog (Pageable pageable);
    Page<Blog> listBlog (String query,Pageable pageable);
    List<Blog> listRecommendBlogTop(Integer size);
//    存储 Blog
    Blog saveBlog(Blog blog);
//    更新 Blog
    Blog updateBlog(Long id,Blog blog);
//    删除 Blog
    void deleteBlog(Long id);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

//    归档
    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
