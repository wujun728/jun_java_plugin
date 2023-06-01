package com.doumuxie.controller;

import com.aliyun.oss.OSSClient;
import com.doumuxie.dto.FileDto;
import com.doumuxie.utils.AliyunOSSClientUtil;
import com.doumuxie.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author doumuxie
 * @version 1.0
 * @date 2020/1/20 19:03
 * @description 文件上传
 **/
@RestController
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultUtil upload(@RequestParam("file") MultipartFile file) {
        logger.info("开始执行upload方法");
        if (file.isEmpty()) {
            return ResultUtil.error("文件不存在");
        }

        logger.info("初始化OSSClient");
        //初始化OSSClient
        OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
        logger.info("开始上传文件====>" +file.getOriginalFilename());
        FileDto dto = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file);
        if (dto == null) {
            return ResultUtil.error("文件上传失败");
        }
        return ResultUtil.success(dto);
    }

}
