package com.example.my_blog.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HandleErrorController {
    //    @RequestMapping (value = "/", method = RequestMethod.GET)
    @GetMapping("/index")
    public String handleError() {
        return "index";
    }
}
