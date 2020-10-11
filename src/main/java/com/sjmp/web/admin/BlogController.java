package com.sjmp.web.admin;

import com.sjmp.po.Blog;
import com.sjmp.po.User;
import com.sjmp.service.BlogService;
import com.sjmp.service.TagService;
import com.sjmp.service.TypeService;
import com.sjmp.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author: sjmp1573
 * @date: 2020/10/1 9:50
 * @description:
 */

@Controller
//统一全局资源路径 /admin 下
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LSIT="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

//    点击 admin/blogs 页面下的删除按钮，浏览器会发送一个 URL 请求，  服务器映射对应函数下执行
    @GetMapping("/blogs/{id}/input")
//    @PathVariable 获取请求连接中的博客 id，spring自动为Model创建实例，并作为controller的入参，后台要从控制层直接返回前端所需的数据。
//    这里将前端所需要的页面数据（flag，title，content，type，tags,firstPicture...）
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    // 新增 blog
    @PostMapping("/blogs")
    private String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if(b == null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LSIT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LSIT;
    }
}
