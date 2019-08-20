package com.zs.markdowndemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface ArticleService {
    public String saveImage(MultipartFile file);

    byte[] loadBytes(String url);
}
