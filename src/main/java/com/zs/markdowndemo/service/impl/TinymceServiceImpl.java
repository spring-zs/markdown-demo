package com.zs.markdowndemo.service.impl;

import com.zs.markdowndemo.config.FileProperties;
import com.zs.markdowndemo.service.ArticleService;
import com.zs.markdowndemo.service.TinymceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service("tinymceService")
public class TinymceServiceImpl implements TinymceService {
    @Autowired
    private FileProperties fileProperties;

    @Override
    public String saveImage(MultipartFile file) {
        try {
            //定义相对路径
            String relativePath = File.separator + getToday() + File.separator;
            String path = fileProperties.filePath + relativePath;
            String originalFileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));


            //随机生成新的文件名，防止文件名冲突或者中文乱码问题
            String newFileName = originalFileName.substring(0,originalFileName.lastIndexOf("."))+"-" +System.currentTimeMillis() + suffix;

            String diskPath = path+newFileName;

            File dest = new File(diskPath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入

            //返回虚拟路径
            String url =("image"+relativePath+newFileName).replaceAll("\\\\","\\/");

            return url;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] loadBytes(String url) {
        System.out.println("---------------------------------------url:"+url);
        return new byte[0];
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");


    private String getToday() {
        return sdf.format(new Date());

    }


}
