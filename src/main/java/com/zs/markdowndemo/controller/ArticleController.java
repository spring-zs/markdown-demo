package com.zs.markdowndemo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zs.markdowndemo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);


    private  static final String IMAGE_URL_PRE ="http://access-usf.jd.com:8089/file/viewImage?key=";


    @Autowired
    private ArticleService articleService;

    @RequestMapping("/display")
    public String uploadImage(){
        return "display";
    }

    @ResponseBody
    @RequestMapping("/uploadImage")
    public String uploadImage(HttpSession session, @RequestParam(value = "editormd-image-file") MultipartFile file) {

        LOGGER.info("========================上传图片开始=========================");
        JSONObject res = new JSONObject();
        if (file.isEmpty()){
            res.put("success", 0);
            res.put("message", "无效的图片" );
        } else{

            String url = articleService.saveImage(file);
            if (url != null){
                res.put("success", 1);
                res.put("message", "上传成功");
                res.put("url",url);
            }else{
                res.put("success", 0);
                res.put("message", "上传失败" );
            }


        }

        return res.toString();
    }


    /**
     在线预览图片
     */
    @RequestMapping("/viewImage")
    @ResponseBody
    public  void  viewImage(HttpServletRequest request, HttpServletResponse response, String url){
        BufferedInputStream bis = null;
        OutputStream os = null;
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        byte[] file = articleService.loadBytes(url);
        try {
            os = response.getOutputStream();
            os.write(file);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }




}