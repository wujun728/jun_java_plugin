package com.jun.plugin.qiniu.utils;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 公共工具类
 * Created by kzyuan on 2017/6/19.
 */
public class CommonUtil {

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    public File multipartToFile(MultipartFile multfile) {

        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        //这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        return file;
    }

}
