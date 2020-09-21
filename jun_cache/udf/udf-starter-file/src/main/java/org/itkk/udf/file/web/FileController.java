/**
 * FileController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.web;

import org.apache.commons.collections4.CollectionUtils;
import org.itkk.udf.core.ApplicationConfig;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.domain.file.FileInfo;
import org.itkk.udf.core.domain.file.FileParam;
import org.itkk.udf.file.FileProperties;
import org.itkk.udf.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 : FileController
 *
 * @author Administrator
 */
@RestController
public class FileController implements IFileController {

    /**
     * 描述 : applicationConfig
     */
    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 描述 : fileProperties
     */
    @Autowired
    private FileProperties fileProperties;

    /**
     * 描述 : fileService
     */
    @Autowired
    private FileService fileService;

    @Override
    public RestResponse<FileInfo> upload(String pathCode, MultipartFile file) throws IOException {
        return new RestResponse<>(fileService.upload(pathCode, file));
    }

    @Override
    public RestResponse<List<FileInfo>> batchUpload(String pathCode, List<MultipartFile> files) throws IOException {
        List<FileInfo> infos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(files)) {
            for (MultipartFile file : files) {
                infos.add(fileService.upload(pathCode, file));
            }
        }
        return new RestResponse<>(infos);
    }

    @Override
    public RestResponse<FileProperties> properties() {
        return new RestResponse<>(fileProperties);
    }

    @Override
    public void download(@RequestBody FileParam fileParam, HttpServletResponse response)
            throws IOException {
        this.loadFile(fileParam.getId(), response);
    }

    @Override
    public RestResponse<FileInfo> info(@RequestBody FileParam fileParam) throws IOException {
        return new RestResponse<>(this.fileService.getFileInfo(fileParam.getId()));
    }

    /**
     * 描述 : 加载文件
     *
     * @param fileId   相对路径
     * @param response 响应对象
     * @throws IOException IOException
     */
    private void loadFile(String fileId, HttpServletResponse response) throws IOException {
        //获得文件信息
        FileInfo fileInfo = this.fileService.getFileInfo(fileId);
        //设置response
        response.setContentLengthLong(fileInfo.getSize());
        response.setCharacterEncoding(applicationConfig.getEncoding());
        response.setContentType(fileInfo.getContentType());
        //获得rootPath
        String rootPath = fileProperties.getRootPath();
        //拼接绝对路径(目录)
        String absolutePath = rootPath + fileInfo.getRelativePath();
        //获得资源对象
        FileSystemResource fsr = new FileSystemResource(absolutePath);
        //输出文件
        final int buffInt = 1024;
        byte[] buff = new byte[buffInt];
        OutputStream os = null;
        BufferedInputStream bis = null;
        try { //NOSONAR
            os = response.getOutputStream();
            bis = new BufferedInputStream(fsr.getInputStream());
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

}
