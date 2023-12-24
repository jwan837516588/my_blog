package com.example.my_blog.web.admin;

import com.example.my_blog.coustom_exception.NotFoundException;
import com.example.my_blog.entity.Type;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CategoryService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller("AdminCategory")
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String category(@PageableDefault(size = 5, sort = {"typeId"}, direction = Sort.Direction.DESC) Pageable pageable, Model model
    ) {
        model.addAttribute("page", categoryService.queryCategory(pageable));
        return "admin/categories";
    }

    @RequestMapping(method = RequestMethod.GET, path = {"/input/{id}", "/input"})
    public String input(HttpServletRequest request, Model model, @PathVariable(required = false) Long id) {
        String uri = request.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/"));
        if ("/input".equals(path)) {
            model.addAttribute("type", new Type());
        } else {
            Type type = categoryService.queryCategoryById(id);
            if (type == null) {
                throw new NotFoundException("The Category does not exist.");
            }
            model.addAttribute("type", type);
        }
        return "admin/add_categories";
    }

    @PostMapping
    public String input(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (categoryService.countCategory(type) > 0) {
            String error = String.format("%s already exist", type.getName());
            result.rejectValue("name", "nameError", error);
            return "admin/add_categories";
        }

        Type t = categoryService.saveCategory(type);

        if (t == null) {
            attributes.addFlashAttribute("message", "Operate successful!");
        } else {
            attributes.addFlashAttribute("message", "Operate failed.");
        }

        return "redirect:/admin/categories";
    }

    @PostMapping("/edit")
    public String edit(@Valid Type type, RedirectAttributes attributes) {
        Type compare = categoryService.queryCategoryById(type.getTypeId());
        // save only if type name changed
        Type t = null;
        if (!type.getName().equals(compare.getName())) {
            t = categoryService.saveCategory(type);
        }
        if (t != null) {
            attributes.addFlashAttribute("message", "Operate successful!");
        } else {
            attributes.addFlashAttribute("message", "Operate failed.");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        if (blogService.countBlogsByTypeId(id)>0) {
            attributes.addFlashAttribute("errorMessage",
                    "Please delete the related blogs first.");
        }
        else categoryService.deleteCategoryById(id);

        return "redirect:/admin/categories";
    }
}
