package com.zs.markdowndemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
  * 图片绝对地址与虚拟地址映射
  */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.filePath}")
    private String filePath;

 @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
     //和页面有关的静态目录都放在项目的static目录下
     registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//文件磁盘图片url 映射
//配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+filePath);

 }




}