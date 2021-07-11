package com.battcn.boot.extend.configuration.oss.cloud.tencent;

import com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate;
import com.battcn.boot.extend.configuration.oss.TencentStorageOperation;
import com.battcn.boot.extend.configuration.oss.properties.TencentStorageProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.TENCENT_STORAGE_OPERATION;


/**
 * OSS自动配置
 *
 * @author Levin
 * @since 2018-09-18 12:24
 **/
@Configuration
@EnableConfigurationProperties({TencentStorageProperties.class})
@ConditionalOnProperty(prefix = ExtendBeanTemplate.OSS_CONFIG_PREFIX_TENCENT, name = "enabled", havingValue = "true")
public class TencentOssAutoConfiguration {


    @Bean
    public COSClient cosClient(TencentStorageProperties properties) {
        COSCredentials credentials = new BasicCOSCredentials(properties.getAccessKey(), properties.getSecretKey());
        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig(new Region(properties.getRegion()));
        return new COSClient(credentials, clientConfig);
    }

    @Bean(TENCENT_STORAGE_OPERATION)
    public TencentStorageOperation tencentStorageOperation(COSClient cosClient, TencentStorageProperties properties) {
        return new TencentStorageOperation(cosClient, properties);
    }


}