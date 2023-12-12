package com.example.my_blog.service;

import com.example.my_blog.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User checkUser(String username, String pswd);
}
