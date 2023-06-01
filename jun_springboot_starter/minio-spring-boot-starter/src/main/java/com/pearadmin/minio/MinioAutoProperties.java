package com.pearadmin.minio;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Minio 配置属性
 *
 * @author lihao3
 * @version 1.0.0
 */
@Validated
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioAutoProperties {

    /**
     * 服务地址
     */
    @NotEmpty(message = "minio服务地址不可为空")
    @URL(message = "minio服务地址格式错误")
    private String url;

    /**
     * 认证账户
     */
    @NotEmpty(message = "minio认证账户不可为空")
    private String accessKey;

    /**
     * 认证密码
     */
    @NotEmpty(message = "minio认证密码不可为空")
    private String secretKey;

    /**
     * 桶名称, 优先级最低
     */
    private String bucket;

    /**
     * 桶不在的时候是否新建桶
     */
    private boolean createBucket = true;

    /**
     * 启动的时候检查桶是否存在
     */
    private boolean checkBucket = true;

    /**
     * 设置HTTP连接、写入和读取超时。值为0意味着没有超时
     * HTTP连接超时，以毫秒为单位。
     */
    private long connectTimeout;

    /**
     * 设置HTTP连接、写入和读取超时。值为0意味着没有超时
     * HTTP写超时，以毫秒为单位。
     */
    private long writeTimeout;

    /**
     * 设置HTTP连接、写入和读取超时。值为0意味着没有超时
     * HTTP读取超时，以毫秒为单位。
     */
    private long readTimeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public boolean isCreateBucket() {
        return createBucket;
    }

    public void setCreateBucket(boolean createBucket) {
        this.createBucket = createBucket;
    }

    public boolean isCheckBucket() {
        return checkBucket;
    }

    public void setCheckBucket(boolean checkBucket) {
        this.checkBucket = checkBucket;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
}
