package com.pearadmin.minio.server;

import com.pearadmin.minio.MinioAutoProperties;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Minio server interface impl
 *
 * @author lihao3
 * @version 1.0.0
 */
@Service
public class MinioTemplateImpl implements MinioTemplate {

    private static final Logger log = LoggerFactory.getLogger(MinioTemplateImpl.class);

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioAutoProperties minioAutoProperties;

    @Override
    public Boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("检查桶是否存在失败!", e);
        }
    }

    @Override
    public void createBucket(String bucketName) {
        if (!this.bucketExists(bucketName)) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                throw new RuntimeException("创建桶失败!", e);
            }
        }
    }

    @Override
    public String putObject(MultipartFile file) {
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(Objects.requireNonNull(file.getOriginalFilename()));
        // 开始上传
        this.putMultipartFile(minioAutoProperties.getBucket(), fileName, file);
        return minioAutoProperties.getUrl() + "/" + minioAutoProperties.getBucket() + "/" + fileName;
    }

    @Override
    public String putObject(String objectName, InputStream inputStream, String contentType) {
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(objectName);
        // 开始上传
        this.putInputStream(minioAutoProperties.getBucket(), fileName, inputStream, contentType);
        return minioAutoProperties.getUrl() + "/" + minioAutoProperties.getBucket() + "/" + fileName;
    }

    @Override
    public String putObject(String objectName, byte[] bytes, String contentType) {
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(objectName);
        // 开始上传
        this.putBytes(minioAutoProperties.getBucket(), fileName, bytes, contentType);
        return minioAutoProperties.getUrl() + "/" + minioAutoProperties.getBucket() + "/" + fileName;
    }

    @Override
    public String putObject(String objectName, MultipartFile file) {
        // 给文件名添加时间戳防止重复
        objectName = getFileName(objectName);
        // 开始上传
        this.putMultipartFile(minioAutoProperties.getBucket(), objectName, file);
        return minioAutoProperties.getUrl() + "/" + minioAutoProperties.getBucket() + "/" + objectName;
    }

    @Override
    public String putObject(String bucketName, String objectName, MultipartFile file) {
        // 先创建桶
        this.createBucket(bucketName);
        // 给文件名添加时间戳防止重复
        objectName = getFileName(objectName);
        // 开始上传
        this.putMultipartFile(bucketName, objectName, file);
        return minioAutoProperties.getUrl() + "/" + bucketName + "/" + objectName;
    }

    @Override
    public String putObject(String bucketName, String objectName, InputStream inputStream, String contentType) {
        // 先创建桶
        this.createBucket(bucketName);
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(objectName);
        // 开始上传
        this.putInputStream(bucketName, fileName, inputStream, contentType);
        return minioAutoProperties.getUrl() + "/" + bucketName + "/" + fileName;
    }

    @Override
    public String putObject(String bucketName, String objectName, byte[] bytes, String contentType) {
        // 先创建桶
        this.createBucket(bucketName);
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(objectName);
        // 开始上传
        this.putBytes(bucketName, fileName, bytes, contentType);
        return minioAutoProperties.getUrl() + "/" + bucketName + "/" + fileName;
    }

    @Override
    public String putObject(String objectName, File file, String contentType) {
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(objectName);
        // 开始上传
        this.putFile(minioAutoProperties.getBucket(), fileName, file, contentType);
        return minioAutoProperties.getUrl() + "/" + minioAutoProperties.getBucket() + "/" + fileName;
    }

    @Override
    public String putObject(String bucketName, String objectName, File file, String contentType) {
        // 先创建桶
        this.createBucket(bucketName);
        // 给文件名添加时间戳防止重复
        String fileName = getFileName(objectName);
        // 开始上传
        this.putFile(bucketName, fileName, file, contentType);
        return minioAutoProperties.getUrl() + "/" + bucketName + "/" + fileName;
    }

    @Override
    public Boolean checkFileIsExist(String objectName) {
        return this.checkFileIsExist(minioAutoProperties.getBucket(), objectName);
    }

    @Override
    public Boolean checkFolderIsExist(String folderName) {
        return this.checkFolderIsExist(minioAutoProperties.getBucket(), folderName);
    }

    @Override
    public Boolean checkFileIsExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build()
            );
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @Override
    public Boolean checkFolderIsExist(String bucketName, String folderName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs
                            .builder()
                            .bucket(minioAutoProperties.getBucket())
                            .prefix(folderName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && folderName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @Override
    public InputStream getObject(String objectName) {
        return this.getObject(minioAutoProperties.getBucket(), objectName);
    }

    @Override
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return minioClient
                    .getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("根据文件名获取流失败!", e);
        }
    }

    @Override
    public InputStream getObjectByUrl(String url) {
        try {
            return new URL(url).openStream();
        } catch (IOException e) {
            throw new RuntimeException("根据URL获取流失败!", e);
        }
    }

    @Override
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException("获取全部存储桶失败!", e);
        }
    }

    @Override
    public Optional<Bucket> getBucket(String bucketName) {
        try {
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (Exception e) {
            throw new RuntimeException("根据存储桶名称获取信息失败!", e);
        }
    }

    @Override
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("根据存储桶名称删除桶失败!", e);
        }
    }

    @Override
    public void removeObject(String objectName) {
        this.removeObject(minioAutoProperties.getBucket(), objectName);
    }

    @Override
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败!", e);
        }
    }

    /**
     * 上传MultipartFile通用方法
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @param file       文件
     */
    private void putMultipartFile(String bucketName, String objectName, MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("文件流获取错误", e);
        }
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(file.getContentType())
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 上传InputStream通用方法
     *
     * @param bucketName  桶名称
     * @param objectName  文件名
     * @param inputStream 文件流
     */
    private void putInputStream(String bucketName, String objectName, InputStream inputStream, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(contentType)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 上传 bytes 通用方法
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @param bytes      字节编码
     */
    private void putBytes(String bucketName, String objectName, byte[] bytes, String contentType) {
        // 字节转文件流
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(contentType)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 上传 file 通用方法
     *
     * @param bucketName
     * @param objectName
     * @param file
     * @param contentType
     */
    private void putFile(String bucketName, String objectName, File file, String contentType) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(contentType)
                            .stream(fileInputStream, fileInputStream.available(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("文件上传错误", e);
        }
    }

    /**
     * 生成唯一ID
     *
     * @param objectName
     * @return
     */
    private static String getFileName(String objectName) {
        //判断文件最后一个点所在的位置
        int lastIndexOf = objectName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return String.format("%s_%s", objectName, System.currentTimeMillis());
        } else {
            // 获取文件前缀,已最后一个点进行分割
            String filePrefix = objectName.substring(0, objectName.lastIndexOf("."));
            // 获取文件后缀,已最后一个点进行分割
            String fileSuffix = objectName.substring(objectName.lastIndexOf(".") + 1);
            // 组成唯一文件名
            return String.format("%s_%s.%s", filePrefix, System.currentTimeMillis(), fileSuffix);
        }
    }
}