package com.example.my_blog.service.impl;

import com.example.my_blog.dao.UserRepository;
import com.example.my_blog.entity.User;
import com.example.my_blog.service.UserService;
import com.example.my_blog.utils.MD5Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String pswd) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.MD5(pswd));
    }
}
