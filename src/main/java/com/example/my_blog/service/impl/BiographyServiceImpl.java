package com.example.my_blog.service.impl;

import com.example.my_blog.dao.BiographyRepository;
import com.example.my_blog.entity.Biography;
import com.example.my_blog.service.BiographyService;
import com.example.my_blog.utils.CastUtils;
import com.example.my_blog.utils.MarkdownUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import java.util.List;

@Service
public class BiographyServiceImpl implements BiographyService {
    @Resource
    private BiographyRepository biographyRepository;

    @Override
    public Biography queryBiography() {
        List<Biography> biographies = biographyRepository.findAll();
        return ListUtils.isEmpty(biographies) ? new Biography() : biographies.get(0);
    }

    @Override
    public Biography queryConvertedBiography() {
        Biography biography = queryBiography();
        biography.setContent(MarkdownUtils.markdownToHtmlExtension(biography.getContent()));
        return biography;
    }

    @Override
    @Transactional
    public Biography saveBiography(Biography biography) {

        return biographyRepository.save(biography);
    }
}
