package com.sjmp.web.admin;

import com.sjmp.po.Type;
import com.sjmp.service.TypeService;
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
public class TypeController {

    @Autowired // 将 typeService 注入
    private TypeService typeService;


    //@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)规定前端 pageable 的格式！
    // 传入 Pageable 页面参数 size 是一条多少条，按主键 id 进行降序排列
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//        Model 是前端页面展示的一个组件
        Page<Type> types = typeService.listType(pageable);
        model.addAttribute("page", types);
        return "admin/types";
    }


    //    发送 /types/input 请求后，跳转到 admin/types-input 页面
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //    编辑 Type
    @GetMapping("types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    // 新增对象，可能返回 null
    @PostMapping("/types") //@Valid 与 Type 中的 @NotBlank 搭配使用，校验 Type 对象，BindingResult result 可拿到校验结果
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
//        校验数据库是否已经包含了该分类的
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    // 新增对象，可能返回 null
    @PostMapping("/types/{id}") //@Valid 与 Type 中的 @NotBlank 搭配使用
    public String post(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
//        校验数据库是否已经包含了该分类的
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
