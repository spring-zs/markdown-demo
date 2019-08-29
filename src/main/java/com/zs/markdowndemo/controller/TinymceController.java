package com.zs.markdowndemo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zs.markdowndemo.service.ArticleService;
import com.zs.markdowndemo.service.TinymceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/tinymce-eiditor")
public class TinymceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TinymceController.class);

 private static String encodeHtml;


    @Autowired
    private TinymceService tinymceService;

//    @RequestMapping("/display")
//    public String uploadImage(){
//        return "display";
//    }

    @ResponseBody
    @PostMapping("/file_upload")
    public String uploadImage(HttpSession session,  MultipartFile file) {

        LOGGER.info("========================上传图片开始=========================");
        JSONObject res = new JSONObject();

        if (file.isEmpty()){
            res.put("success", 0);
            res.put("message", "无效的图片" );
        } else{

            String url = tinymceService.saveImage(file);
            if (url != null){
                res.put("success", 1);
                res.put("message", "上传成功");
                //tinymce插件返回值必须使用location字段
                res.put("location",url);
            }else{
                res.put("success", 0);
                res.put("message", "上传失败" );
            }


        }

        return res.toJSONString();
    }


    @ResponseBody
    @PostMapping("/content")
    public String uploadImage(String mytextarea) {

        LOGGER.info("========================content=========================");
        // 编码
         encodeHtml = Base64.getEncoder().encodeToString(mytextarea.getBytes(StandardCharsets.UTF_8));



        return "okkkk";
    }


    /**
     在线预览图片
     */
    @RequestMapping("/read")
    @ResponseBody
    public  ModelMap  viewImage(){
        ModelMap modelMap = new ModelMap();

        // 解码
        byte[] decode = Base64.getDecoder().decode(encodeHtml);
        String originHTML = new String(decode, StandardCharsets.UTF_8);


        modelMap.addAttribute("html",originHTML);
        return modelMap;
    }





}