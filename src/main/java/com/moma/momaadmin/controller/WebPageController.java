package com.moma.momaadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("web")
public class WebPageController {

    @RequestMapping("code")
    public String goIndex(Model model){
        model.addAttribute("username","litenghao");
        model.addAttribute("code","123456");
        return "code-mail";
    }
}
