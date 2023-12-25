package com.example.my_blog.web;

import com.example.my_blog.entity.Blog;
import com.example.my_blog.service.BlogService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ImageController {
    @Value("${uploadDir}")
    private String uploadFolder;

    @Autowired
    private BlogService blogService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/image/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response)
            throws IOException {
        log.info("Id :: " + id);
        Blog blog = blogService.queryBlogById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(blog.getProfile());
        response.getOutputStream().close();
    }
}
