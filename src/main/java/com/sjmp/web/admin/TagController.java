package com.sjmp.web.admin;

import com.sjmp.po.Tag;
import com.sjmp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/**
 * @author: sjmp1573
 * @date: 2020/10/1 11:25
 * @description:
 */


@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired // 将 tagService 注入
    private TagService tagService;

    @GetMapping("/tags")
    //传入 Pageable 页面参数 size 是一条多少条，按主键 id 进行降序排列
    public String tags(@PageableDefault(size = 10 , sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable,
                       Model model){

        Page<Tag> tags = tagService.listTag(pageable);
//        Model 是前端页面展示的一个组件
        model.addAttribute("page",tags);
        return "admin/tags";
    }


//    发送 /tags/input 请求后，跳转到 admin/tags-input 页面
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

//    编辑 Tag
    @GetMapping("tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

// 新增对象，可能返回 null
    @PostMapping("/tags") //@Valid 与 Tag 中的 @NotBlank 搭配使用
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
//        校验数据库是否已经包含了该分类的
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags" ;
    }

    // 新增对象，可能返回 null
    @PostMapping("/tags/{id}") //@Valid 与 Tag 中的 @NotBlank 搭配使用
    public String post(@Valid Tag tag, BindingResult result,
                       @PathVariable Long id,
                       RedirectAttributes attributes){

//        校验数据库是否已经包含了该分类的
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags" ;
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
