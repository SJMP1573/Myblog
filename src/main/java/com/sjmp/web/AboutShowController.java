package com.sjmp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: sjmp1573
 * @date: 2020/10/6 14:37
 * @description:
 */

@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
     return "about";
    }
}
