package com.example.my_blog.service;

import com.example.my_blog.entity.Biography;

public interface BiographyService {

    Biography queryBiography();
    Biography queryConvertedBiography();

    Biography saveBiography(Biography biography);
}
