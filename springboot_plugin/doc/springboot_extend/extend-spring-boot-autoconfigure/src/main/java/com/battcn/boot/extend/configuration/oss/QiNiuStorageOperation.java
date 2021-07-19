package com.battcn.boot.extend.configuration.oss;

import com.battcn.boot.extend.configuration.oss.cloud.qiniu.QiNiuScope;
import com.battcn.boot.extend.configuration.oss.cloud.qiniu.connection.QiNiuConnectionFactory;
import com.battcn.boot.extend.configuration.oss.domain.DownloadResponse;
import com.battcn.boot.extend.configuration.oss.domain.StorageItem;
import com.battcn.boot.extend.configuration.oss.domain.StorageResponse;
import com.battcn.boot.extend.configuration.oss.properties.QiNiuStorageProperties;
import com.qiniu.cdn.CdnManager;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.List;


/**
 * @author Levin
 */
@Slf4j
public class QiNiuStorageOperation implements StorageOperation {

    private final UploadManager uploadManager;
    private final BucketManager bucketManager;
    private final CdnManager cdnManager;
    private final QiNiuStorageProperties properties;
    private final QiNiuConnectionFactory connectionFactory;

    @Autowired
    public QiNiuStorageOperation(QiNiuStorageProperties properties, QiNiuConnectionFactory connectionFactory) {
        this.properties = properties;
        this.connectionFactory = connectionFactory;
        this.uploadManager = this.connectionFactory.getUploadManager();
        this.bucketManager = this.connectionFactory.getBucketManager();
        this.cdnManager = this.connectionFactory.getCdnManager();
    }


    @Override
    public DownloadResponse download(String fileName) {
        String domainOfBucket = this.connectionFactory.getDomain(properties.getBucket());
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
            String format = String.format("%s/%s", domainOfBucket, encodedFileName);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public DownloadResponse download(String bucketName, String fileName) {
        return null;
    }


    @Override
    public void download(String bucketName, String fileName, File file) {

    }


    @Override
    public void download(String fileName, File file) {

    }


    @Override
    public List<StorageItem> list() {
        return null;
    }


    @Override
    public void rename(String oldName, String newName) {

    }


    @Override
    public void rename(String bucketName, String oldName, String newName) {

    }


    @Override
    public StorageResponse upload(String fileName, byte[] content) {
        return upload(properties.getBucket(), fileName, content);
    }


    @Override
    public StorageResponse upload(String bucketName, String fileName, InputStream content) {
        try {
            String upToken = getUploadToken(bucketName, fileName, QiNiuScope.DEFAULT);
            Response response = uploadManager.put(content, fileName, upToken, null, null);
            if (!response.isOK()) {
                return StorageResponse.error(response.error);
            }
            return StorageResponse.success(StorageItem.builder()
                    .name(fileName)
                    .size((long) response.body().length)
                    .path(response.url()).build());
        } catch (QiniuException e) {
            log.error("[文件上传异常]", e);
            return StorageResponse.error(e.getLocalizedMessage());
        }
    }


    @Override
    public StorageResponse upload(String bucketName, String fileName, byte[] content) {
        try {
            String upToken = getUploadToken(bucketName, fileName, QiNiuScope.DEFAULT);
            Response response = uploadManager.put(content, fileName, upToken);
            if (!response.isOK()) {
                return StorageResponse.error(response.error);
            }
            return StorageResponse.success(StorageItem.builder()
                    .size((long) response.body().length)
                    .name(fileName)
                    .path(response.url()).build());
        } catch (QiniuException e) {
            log.error("[文件上传异常]", e);
            return StorageResponse.error(e.getLocalizedMessage());
        }
    }


    @Override
    public void remove(String fileName) {
        remove(properties.getBucket(), fileName);
    }


    @Override
    public void remove(String bucketName, String fileName) {
        try {
            bucketManager.delete(bucketName, fileName);
        } catch (QiniuException e) {
            log.error("[文件移除异常]", e);
        }
    }


    @Override
    public void remove(String bucketName, Path path) {
        remove(bucketName, path.toString());
    }

    private String getUploadToken(String bucket, String key, QiNiuScope scope) {
        return this.connectionFactory.getUploadToken(bucket, key, scope);
    }
}
