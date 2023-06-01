package com.pearadmin.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Minio 自动配置
 *
 * @author lihao3
 * @version 1.0.0
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
@EnableConfigurationProperties(MinioAutoProperties.class)
public class MinioAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MinioAutoConfiguration.class);

    @Resource
    private MinioAutoProperties minioAutoProperties;

    @Bean
    public MinioClient minioClient() {
        log.info("开始初始化MinioClient, url为{}, accessKey为:{}", minioAutoProperties.getUrl(), minioAutoProperties.getAccessKey());
        MinioClient minioClient = MinioClient
                .builder()
                .endpoint(minioAutoProperties.getUrl())
                .credentials(minioAutoProperties.getAccessKey(), minioAutoProperties.getSecretKey())
                .build();

        minioClient.setTimeout(
                minioAutoProperties.getConnectTimeout(),
                minioAutoProperties.getWriteTimeout(),
                minioAutoProperties.getReadTimeout()
        );
        // Start detection
        if (minioAutoProperties.isCheckBucket()) {
            log.info("checkBucket为{}, 开始检测桶是否存在", minioAutoProperties.isCheckBucket());
            String bucketName = minioAutoProperties.getBucket();
            if (!checkBucket(bucketName, minioClient)) {
                log.info("文件桶[{}]不存在, 开始检查是否可以新建桶", bucketName);
                if (minioAutoProperties.isCreateBucket()) {
                    log.info("createBucket为{},开始新建文件桶", minioAutoProperties.isCreateBucket());
                    createBucket(bucketName, minioClient);
                }
            }
            log.info("文件桶[{}]已存在, minio客户端连接成功!", bucketName);
        } else {
            throw new RuntimeException("桶不存在, 请检查桶名称是否正确或者将checkBucket属性改为false");
        }
        return minioClient;
    }

    private boolean checkBucket(String bucketName, MinioClient minioClient) {
        boolean isExists = false;
        try {
            isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("failed to check if the bucket exists", e);
        }
        return isExists;
    }

    private void createBucket(String bucketName, MinioClient minioClient) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("文件桶[{}]新建成功, minio客户端已连接", bucketName);
        } catch (Exception e) {
            throw new RuntimeException("failed to create default bucket", e);
        }
    }
}
