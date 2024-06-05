package cn.centychen.springboot.starter.xxljob.autoconfigure;

import cn.centychen.springboot.starter.xxljob.builder.XxlJobSpringExecutorBuilder;
import cn.centychen.springboot.starter.xxljob.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by cent at 2019-05-08.
 */
@Configuration
@EnableConfigurationProperties({XxlJobProperties.class})
@Slf4j
public class XxlJobAutoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties prop) {
        log.info(">>>>>>>>>>> xxl job config init...");
        return new XxlJobSpringExecutorBuilder()
                .withAdminAddresses(prop.getAdmin().getAdminAddresses())
                .withAppName(prop.getExecutor().getAppName())
                .withIp(prop.getExecutor().getIp())
                .withPort(prop.getExecutor().getPort())
                .withAccessToken(prop.getExecutor().getAccessToken())
                .withLogPath(prop.getExecutor().getLogPath())
                .withLogRetentionDays(prop.getExecutor().getLogRetentionDays())
                .build();
    }
}
