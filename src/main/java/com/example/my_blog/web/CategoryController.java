package com.example.my_blog.web;

import com.example.my_blog.entity.Type;
import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BlogService blogService;

    @GetMapping("/category/{id}")
    public String category(@PathVariable Long id,
                           @PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        List<Type> types = categoryService.queryTop(10000);
        if (!types.isEmpty() && id == -1) {
            id = types.get(0).getTypeId();
        }
        model.addAttribute("activeTypeId", id);
        model.addAttribute("page", blogService.queryBlogs(pageable, new BlogScope(id)));
        model.addAttribute("types", types);
        return "category";
    }

}
