package com.zhang.qiniuDemo.controller;

import com.zhang.qiniuDemo.utils.QiNiuYunDeBugUpload;
import com.zhang.qiniuDemo.utils.QiniuUploadUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/14 10:42
 */
@RestController
@RequestMapping("/dataUrl")
public class QiNiuYunUrlController {

    @PostMapping("/generalUpload")
    public String generalUpload(@RequestParam("file") MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String imageName = new QiniuUploadUtil().upload(originalFilename, file.getBytes());
        return imageName;
    }

    @PostMapping("/debugUpload")
    public String debugUpload(@RequestParam("file") MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.replace(".","-").split("-");
        String imageName = new QiNiuYunDeBugUpload().upload(split[0], file.getBytes());
        return imageName;
    }
}
