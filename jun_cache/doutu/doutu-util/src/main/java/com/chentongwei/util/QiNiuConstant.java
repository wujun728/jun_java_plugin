package com.chentongwei.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 七牛常量，多环境配置
 *
 * @author TongWei.Chen 2017-05-16 15:21:05
 */
@Configuration
@ConfigurationProperties(prefix="qiniu")
@PropertySource("classpath:qiniu.properties")
public class QiNiuConstant {
    //key
    private String accessKey;
    //密钥
    private String secretKey;
    //空间名
    private String bucket;

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

    @Override
    public String toString() {
        return "QiNiuContant [accessKey=" + accessKey + ", secretKey=" + secretKey + ", bucket=" + bucket + "]";
    }
}