package com.example.my_blog.web;

import com.example.my_blog.entity.Blog;
import com.example.my_blog.service.BlogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class FilingController {
    @Resource
    private BlogService blogService;
    @GetMapping("/filing")
    public String filing(Model model) {
        Map<String, List<Blog>> blog = blogService.queryBlogsByYear();
        model.addAttribute("filing", blog);
        model.addAttribute("count", blogService.countBlogs());
        return "filing";
    }
}
