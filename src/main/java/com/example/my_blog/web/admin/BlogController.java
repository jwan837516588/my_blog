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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
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

    @Value("${uploadDir}")
    private String uploadFolder;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
    public String writeBlog(@Valid Blog blog, RedirectAttributes attributes, HttpSession httpSession, HttpServletRequest request, Model model) {
        // query types by given type ids
        blog.setUser((User) httpSession.getAttribute("user"));
        Type type = categoryService.queryCategoryById(blog.getType().getTypeId());
        // save type
        blog.setType(type);
        // query tags by given tag ids
        List<Long> tagIds = CastUtils.convertStringToLongList(blog.getTagIds());
        List<Tag> tags = tagIds != null ? tagService.queryBatchTagsByIds(tagIds) : null;
        // save tags
        blog.setTags(tags);

        // save profile image.
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = blog.getFile().getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + blog.getFile().getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
            }
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(blog.getFile().getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            blog.setProfile(blog.getFile().getBytes());
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
        }

        // save new blog
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "Operation failed.");
        }
        return "redirect:/admin/blog/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlogById(id);
        return "redirect:/admin/blog/list";
    }
}
