package com.example.my_blog.web.admin;

import com.example.my_blog.entity.Type;
import com.example.my_blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String category(@PageableDefault(size = 5, sort = {"typeId"}, direction = Sort.Direction.DESC) Pageable pageable, Model model
    ) {
        model.addAttribute("page", categoryService.queryCategory(pageable));
        return "admin/categories";
    }

    @GetMapping("/input")
    public String input() {
        return "admin/add_categories";
    }

    @PostMapping
    public String input(Type type) {
        categoryService.saveCategory(type);
        return "redirect:/admin/categories";
    }
}
