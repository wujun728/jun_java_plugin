package com.doumuxie.utils;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.doumuxie.dto.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Objects;

/**
 * @author doumuxie
 * @version 1.0
 * @date 2020/1/20 16:08
 * @description 阿里云 oss 对象存储 工具类
 **/
@Component
public class AliyunOSSClientUtil {


    private static Logger logger = LoggerFactory.getLogger(AliyunOSSClientUtil.class);
    /**
     * //阿里云API的内或外网域名
     */
    private static String ENDPOINT;
    /**
     * //阿里云API的密钥Access Key ID
     */
    private static String ACCESS_KEY_ID;
    /**
     * //阿里云API的密钥Access Key Secret
     */
    private static String ACCESS_KEY_SECRET;
    /**
     * //阿里云API的bucket名称
     */
    private static String BUCKET_NAME;
    /**
     * //阿里云API的文件夹名称
     */
    private static String FOLDER;

    /**
     * 获取阿里云OSS客户端对象 (初始化)
     *
     * @return ossClient
     */
    public static OSSClient getOSSClient() {
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }



    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @param key        Bucket下的文件的路径名+文件名 如："default/123.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }


    /**
     * 上传图片至OSS
     *
     * @param ossClient oss连接
     * @param file      MultipartFile 文件
     * @param folder    指定上传文件夹路径
     * @return FileDto   上传结果
     */
    public static FileDto uploadObject2OSS(OSSClient ossClient, MultipartFile file, String folder) {
        return uploadObject2OSS(ossClient, file, BUCKET_NAME, folder);
    }

    /**
     * 上传图片至OSS
     *
     * @param ossClient oss连接
     * @param file      MultipartFile 文件
     * @return FileDto   上传结果
     */
    public static FileDto uploadObject2OSS(OSSClient ossClient, MultipartFile file) {
        return uploadObject2OSS(ossClient, file, BUCKET_NAME, FOLDER);
    }

    /**
     * 上传图片至OSS
     *
     * @param ossClient  oss连接
     * @param file       MultipartFile 文件
     * @param bucketName 存储空间
     * @param folder     文件夹名称
     * @return FileDto   上传结果
     */
    public static FileDto uploadObject2OSS(OSSClient ossClient, MultipartFile file, String bucketName, String folder) {
        logger.info("   上传参数bucketName , folder ===> " + bucketName + " , " + folder);
        FileDto dto = new FileDto();
        try {
            StringBuilder fileUrl = new StringBuilder();
            String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.'));
            String point = ".";
            String uuid = UUIDUtil.getUUID();
            dto.setSize(file.getSize());
            dto.setId(uuid);
            dto.setName(file.getOriginalFilename());

            if (!suffix.startsWith(point)) {
                suffix = point + suffix;
            }
            String fileName = uuid + suffix;
            if (!folder.endsWith("/")) {
                folder = folder.concat("/");
            }
            fileUrl.append(folder).append(fileName);
            ossClient.putObject(bucketName, fileUrl.toString(), file.getInputStream());
            // 文件url
            dto.setUrl(getFileUrl(fileName));
            logger.info("文件上传成功 ===>" + dto.getName());
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 拼接文件url
     *
     * @param fileName 文件名称
     * @return url
     */
    public static String getFileUrl(String fileName) {
        return "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + FOLDER + "/" + fileName;
    }



    /***************************以下setter用于配置注入***************************/

    @Value("${config.aliyun_oss.endpoint}")
    public void setENDPOINT(String ENDPOINT) {
        AliyunOSSClientUtil.ENDPOINT = ENDPOINT;
    }

    @Value("${config.aliyun_oss.access_key_id}")
    public void setAccessKeyId(String accessKeyId) {
        ACCESS_KEY_ID = accessKeyId;
    }

    @Value("${config.aliyun_oss.access_key_secret}")
    public void setAccessKeySecret(String accessKeySecret) {
        ACCESS_KEY_SECRET = accessKeySecret;
    }

    @Value("${config.aliyun_oss.bucket_name}")
    public void setBucketName(String bucketName) {
        BUCKET_NAME = bucketName;
    }

    @Value("${config.aliyun_oss.folder}")
    public void setFOLDER(String FOLDER) {
        AliyunOSSClientUtil.FOLDER = FOLDER;
    }
}
