package com.battcn.boot.extend.configuration.oss.cloud.qiniu;

import com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate;
import com.battcn.boot.extend.configuration.oss.QiNiuStorageOperation;
import com.battcn.boot.extend.configuration.oss.cloud.qiniu.connection.QiNiuConnectionFactory;
import com.battcn.boot.extend.configuration.oss.cloud.qiniu.connection.QiNiuOssClientConnectionFactory;
import com.battcn.boot.extend.configuration.oss.properties.QiNiuStorageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.QI_NIU_STORAGE_OPERATION;

/**
 * @author Levin
 */
@EnableConfigurationProperties({QiNiuStorageProperties.class})
@ConditionalOnProperty(prefix = ExtendBeanTemplate.OSS_CONFIG_PREFIX_QINIU, name = "enabled", havingValue = "true")
public class QiNiuOssAutoConfiguration {

    @Bean
    public QiNiuConnectionFactory qiNiuConnectionFactory(QiNiuStorageProperties properties) {
        return new QiNiuOssClientConnectionFactory(properties);
    }

    @Bean(QI_NIU_STORAGE_OPERATION)
    public QiNiuStorageOperation qiNiuStorageOperation(QiNiuStorageProperties properties, QiNiuConnectionFactory qiNiuConnectionFactory) {
        return new QiNiuStorageOperation(properties, qiNiuConnectionFactory);
    }
}