package com.example.my_blog.web.admin;

import com.example.my_blog.entity.Biography;
import com.example.my_blog.entity.User;
import com.example.my_blog.service.BiographyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/biography")
public class BiographyController {
    @Autowired
    private BiographyService biographyService;

    @GetMapping
    public String biographyPage(Model model) {
        // get current biography info
        Biography biography = biographyService.queryBiography();

        model.addAttribute("biography", biography);

        return "admin/biography";
    }

    @PostMapping
    public String writeBiography(@Valid Biography biography, RedirectAttributes attributes, HttpSession httpSession) {
        // query types by given type ids
        biography.setUser((User) httpSession.getAttribute("user"));
        // save new biography
        Biography b = biographyService.saveBiography(biography);
        return "redirect:/admin/blog/list";
    }

}
