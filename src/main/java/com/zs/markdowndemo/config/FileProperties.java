package com.zs.markdowndemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileProperties {
    @Value("${upload.filePath}")
    public String filePath;
}
