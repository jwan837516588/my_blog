package com.example.my_blog.web;

import com.example.my_blog.service.BlogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {
    @Resource
    private BlogService blogService;

    @GetMapping("/about")
    public String aboutMe() {
        return "about_me";
    }
}
