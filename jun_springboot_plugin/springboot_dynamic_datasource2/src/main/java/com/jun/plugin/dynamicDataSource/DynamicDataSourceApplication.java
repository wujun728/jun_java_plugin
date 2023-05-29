package com.jun.plugin.dynamicDataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jun.plugin.dynamicDataSource.aop.DynamicDataSourceAnnotationAdvisor;
import com.jun.plugin.dynamicDataSource.aop.DynamicDataSourceAnnotationInterceptor;
import com.jun.plugin.dynamicDataSource.register.DynamicDataSourceRegister;

@Import(DynamicDataSourceRegister.class)
@MapperScan("com.jun.plugin.dynamicDataSource.repository")
@SpringBootApplication
@EnableTransactionManagement
public class DynamicDataSourceApplication {
    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }
    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApplication.class, args);
    }
}
