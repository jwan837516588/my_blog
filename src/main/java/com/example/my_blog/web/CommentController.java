package com.example.my_blog.web;

import com.example.my_blog.entity.Blog;
import com.example.my_blog.entity.Comment;
import com.example.my_blog.entity.User;
import com.example.my_blog.service.BlogService;
import com.example.my_blog.service.CommentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private BlogService blogService;

    @Value("${comment.photo}")
    private String photo;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.queryCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession httpSession) {
        Long blogId = comment.getBlog().getBlogId();
        Blog blog = blogService.queryBlogById(blogId);
        comment.setBlog(blog);
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            comment.setPhoto(user.getPhoto());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        } else comment.setPhoto(photo);
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogId;
    }
}
