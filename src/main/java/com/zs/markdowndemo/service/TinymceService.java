package com.zs.markdowndemo.service;

import org.springframework.web.multipart.MultipartFile;


public interface TinymceService {
    public String saveImage(MultipartFile file);

    byte[] loadBytes(String url);
}
