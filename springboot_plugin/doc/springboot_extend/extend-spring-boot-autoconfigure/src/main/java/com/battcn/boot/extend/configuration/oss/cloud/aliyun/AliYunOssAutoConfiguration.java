package com.battcn.boot.extend.configuration.oss.cloud.aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate;
import com.battcn.boot.extend.configuration.oss.AliYunStorageOperation;
import com.battcn.boot.extend.configuration.oss.endpoint.OssEndpoint;
import com.battcn.boot.extend.configuration.oss.properties.AliYunStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.MINIO_STORAGE_OPERATION;

/**
 * 阿里云 OSS
 *
 * @author Levin
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(AliYunStorageProperties.class)
@ConditionalOnProperty(prefix = ExtendBeanTemplate.OSS_CONFIG_PREFIX_ALIYUN, name = "enabled", havingValue = "true")
public class AliYunOssAutoConfiguration {


    @Bean
    public OSSClient ossClient(AliYunStorageProperties properties) {
        CredentialsProvider provider = new DefaultCredentialProvider(properties.getAccessKey(), properties.getSecretKey());
        return new OSSClient(properties.getEndpoint(), provider, new ClientConfiguration());
    }

    @Bean(MINIO_STORAGE_OPERATION)
    public AliYunStorageOperation aliYunStorageOperation(OSSClient ossClient, AliYunStorageProperties properties) {
        return new AliYunStorageOperation(ossClient, properties);
    }


    @Bean
    public OssEndpoint aliYunOssEndpoint() {
        return new OssEndpoint();
    }
}