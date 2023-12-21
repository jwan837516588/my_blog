package com.example.my_blog.web;

import com.example.my_blog.entity.Tag;
import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class TagController {
    @Resource
    private TagService tagService;
    @Resource
    private BlogService blogService;

    @GetMapping("/tag/{id}")
    public String index(@PathVariable Long id,
                        @PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Tag> tags = tagService.queryTop(10000);
        if (!tags.isEmpty() && id == -1) {
            id = tags.get(0).getTagId();
        }
        model.addAttribute("activeTagId", id);
        model.addAttribute("page", blogService.queryBlogsByTagId(pageable, id));
        model.addAttribute("tags", tags);
        return "tag";
    }

}
