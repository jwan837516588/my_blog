package com.example.my_blog.web.admin;

import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.Tag;
import com.example.my_blog.entity.Type;
import com.example.my_blog.entity.User;
import com.example.my_blog.entity.scope.BlogScope;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CategoryService;
import com.example.my_blog.service.TagService;
import com.example.my_blog.utils.CastUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;


    @GetMapping("/list")
    public String listPage(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, BlogScope blog) {
        model.addAttribute("types", categoryService.findAllCategories());
        model.addAttribute("page", blogService.queryBlogs(pageable, blog));
        return "admin/list";
    }


    /**
     * only render the search block.
     *
     * @param pageable
     * @param model
     * @param blog
     * @return
     */
    @PostMapping("/list/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, BlogScope blog) {
        model.addAttribute("page", blogService.queryBlogs(pageable, blog));
        return "admin/list :: blogList";
    }

    @GetMapping("/post")
    public String postPage(Model model) {
        model.addAttribute("types", categoryService.findAllCategories());
        model.addAttribute("tags", tagService.findAllTags());
        model.addAttribute("blog", new Blog());
        return "admin/post";
    }

    @GetMapping("/post/{id}")
    public String editPostPage(@PathVariable Long id, Model model) {
        model.addAttribute("types", categoryService.findAllCategories());
        model.addAttribute("tags", tagService.findAllTags());
        // get current blog info
        Blog blog = blogService.queryBlogById(id);
        // add tag id to variable tagIds
        List<Tag> tags = blog.getTags();
        if (tags != null) {
            List<Long> tagIds = tags.stream().map(Tag::getTagId).toList();
            blog.setTagIds(CastUtils.convertLongListToString(tagIds));
        }
        model.addAttribute("blog", blog);
        return "admin/post";
    }

    @PostMapping("/post")
    public String createBlog(Blog blog, RedirectAttributes attributes, HttpSession httpSession) {
        // query types by given type ids
        blog.setUser((User) httpSession.getAttribute("user"));
        Type type = categoryService.queryCategoryById(blog.getType().getTypeId());
        blog.setType(type);
//        // Since CascadeType.PERSIST, the blog needs to be saved before setTags.
//        blogService.saveBlog(blog);
        // query tags by given tag ids
        List<Long> tagIds = CastUtils.convertStringToLongList(blog.getTagIds());
        List<Tag> tags = tagIds != null ? tagService.queryBatchTagsByIds(tagIds) : null;
        blog.setTags(tags);
        // save new blog
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        }
        return "redirect:/admin/blog/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlogById(id);
        return "redirect:/admin/blog/list";
    }
}
