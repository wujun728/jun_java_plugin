package com.hope.minio.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @PackageName: com.hope.minio.service
 * @ClassName: MinioService
 * @Author Hope
 * @Date 2020/7/27 9:58
 * @Description: MinioService
 */
public interface MinioService {


    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    void makeBucket(String bucketName);

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filename
     */
    void putObject(String bucketName, String objectName, String filename);

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param stream
     */
    void putObject(String bucketName, String objectName, InputStream stream, String contentType);

    /**
     * 文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    void putObject(String bucketName, MultipartFile multipartFile, String filename);

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    boolean removeObject(String bucketName,String objectName);

    /**
     * 下载文件
     *
     * @param fileName
     * @param originalName
     * @param response
     */
    void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response);

    /**
     * 获取文件路径
     * @param bucketName
     * @param objectName
     * @return
     */
    String getObjectUrl(String bucketName,String objectName);
}
