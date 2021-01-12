package com.sjmp.web.admin;

import com.sjmp.po.User;
import com.sjmp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author: sjmp1573
 * @date: 2020/9/29 6:55
 * @description:
 */

// @Controller只是定义了一个控制器类
// @RequestMapping注解的方法才是处理请求的处理器。
@Controller
@RequestMapping("/admin")
public class LoginController {

//  检查用户名及密码时，需要使用 UserService 中的 checkUser() 方法，所以注入 userService 对象
    @Autowired
    private UserService userService;

    /*
    * @GetMapping用于处理请求方法的GET类型，
    * @PostMapping用于处理请求方法的POST类型
    * @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    * 新方法可以简化为：@GetMapping("/get/{id}")
    *
    * */
    @GetMapping
    public String loginPage(){
        return "admin/login"; // 当输入请求 /admin ，进入 login.html（/admin/login.html）页面
    }

    // 登录 /admin/login.html，传入用户名，密码
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,  // user 与 server 建立的会话！可记录访问者的一些信息！
                        RedirectAttributes attributes){

        User user = userService.checkUser(username, password);//用户密码正确，返回该 user 对象，否则返回 null ！

        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
//            用户不通过验证，将错误参数传到前端！重定向到登录页面！
            attributes.addFlashAttribute("message","用户名或密码错误！");
//            不能使用 Model; Model 存放的时在请求域中，重定向之后是另一个请求，所以拿不到！
            return "redirect:/admin";
        }
    }

    // （/admin/logout）注销当前登录的用户
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
