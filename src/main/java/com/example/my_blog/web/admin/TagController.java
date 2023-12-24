package com.example.my_blog.web.admin;

import com.example.my_blog.coustom_exception.NotFoundException;
import com.example.my_blog.entity.Tag;
import com.example.my_blog.entity.Tag;
import com.example.my_blog.service.TagService;
import com.example.my_blog.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
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

@Controller("AdminTag")
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public String tag(@PageableDefault(size = 5, sort = {"tagId"}, direction = Sort.Direction.DESC) Pageable pageable, Model model
    ) {
        model.addAttribute("page", tagService.queryTag(pageable));
        return "admin/tags";
    }

    @RequestMapping(method = RequestMethod.GET, path = {"/input/{id}", "/input"})
    public String input(HttpServletRequest request, Model model, @PathVariable(required = false) Long id) {
        String uri = request.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/"));
        if ("/input".equals(path)) {
            model.addAttribute("tag", new Tag());
        }
        else {
            Tag tag = tagService.queryTagById(id);
            if (tag == null) {
                throw new NotFoundException("The Tag does not exist.");
            }
            model.addAttribute("tag", tag);
        }
        return "admin/add_tags";
    }

    @PostMapping
    public String input(@Validated Tag tag, BindingResult result, RedirectAttributes attributes) {
        if (tagService.countTag(tag) > 0) {
            String error = String.format("%s already exist", tag.getName());
            result.rejectValue("name", "nameError", error);
            return "admin/add_tags";
        }

        Tag t = tagService.saveTag(tag);
        if (t != null) {
            attributes.addFlashAttribute("message", "Operate successful!");
        } else {
            attributes.addFlashAttribute("message", "Operate failed.");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/edit")
    public String edit(Tag tag) {
        Tag compare = tagService.queryTagById(tag.getTagId());
        if (!tag.getName().equals(compare.getName())) {
            tagService.saveTag(tag);
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        tagService.deleteTagById(id);

        return "redirect:/admin/tags";
    }
}
