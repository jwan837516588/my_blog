package com.example.my_blog.web.admin;

import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String listPage(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, BlogScope blog) {
        model.addAttribute("types", categoryService.findAllCategories());
        model.addAttribute("page", blogService.queryBlog(pageable, blog));
        return "admin/list";
    }


    /**
     * only render the search block.
     * @param pageable
     * @param model
     * @param blog
     * @return
     */
    @PostMapping("/list/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, BlogScope blog) {
        model.addAttribute("page", blogService.queryBlog(pageable, blog));
        return "admin/list :: blogList";
    }

    @GetMapping("/post")
    public String postPage() {

        return "admin/post";
    }
}
