package com.example.my_blog.web;


import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CategoryService;
import com.example.my_blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.queryBlogs(pageable));
        model.addAttribute("types", categoryService.queryTop(6));
        model.addAttribute("tags", tagService.queryTop(10));
        model.addAttribute("recommendBlogs", blogService.queryTopRecommend(8));
        return "index";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.queryBlogs(query, pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getConvertedBlogById(id));
        return "blog";
    }

    @GetMapping("/footer/story")
    public String latestBlog(Model model) {
        model.addAttribute("latestBlogs", blogService.queryTopRecommend(3));
        return "_fragments :: blogList";
    }
}
