package com.example.my_blog.web;

import com.example.my_blog.service.BiographyService;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {
    @Resource
    private BiographyService biographyService;

    @Resource
    private TagService tagService;

    @GetMapping("/me")
    public String aboutMe(Model model) {
        model.addAttribute("biography", biographyService.queryConvertedBiography());
        model.addAttribute("tags", tagService.queryAllTags());
        return "about_me";
    }
}
