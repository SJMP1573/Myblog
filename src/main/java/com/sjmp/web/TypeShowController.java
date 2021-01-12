package com.sjmp.web;

import com.sjmp.po.Type;
import com.sjmp.service.BlogService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: sjmp1573
 * @date: 2020/10/5 21:47
 * @description:
 */

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(10000);
//        -1 是从首页点进去的
        if (id == -1){
            id = types.get(0).getId();
        }
//        根据分类的 ID 查询博客！
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
//        拿到所有的分类
        model.addAttribute("types",types);
//        所有的 blog page，根据 blogQuery 的 type ID ，动态查询对应的博客，listBlog(pageable,blogQuery) 方法复用！
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
