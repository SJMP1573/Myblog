package com.sjmp.web;

import com.sjmp.po.Comment;
import com.sjmp.po.User;
import com.sjmp.service.BlogService;
import com.sjmp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author: sjmp1573
 * @date: 2020/10/4 15:16
 * @description:
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    // 返回一个评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

//    点击发布，返回提交信息
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        // 通过页面拿到一个 session ,再获取到其中的 user 属性。
        User user = (User) session.getAttribute("user");
//        判断 user 属性是否为空，若不为空，则设置其为管理员。
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
//            comment.setNickname(user.getNickname());
        }else{
//            否则设置为普通用户
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
