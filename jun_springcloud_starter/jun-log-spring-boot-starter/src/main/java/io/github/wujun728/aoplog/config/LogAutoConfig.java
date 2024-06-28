package io.github.wujun728.aoplog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wujun
 * @date 2021/3/19
 */
@Configuration
@ComponentScan(basePackages = "io.github.wujun728.aoplog")
@MapperScan(basePackages = "io.github.wujun728.aoplog.mapper")
@ServletComponentScan(basePackages = {"io.github.wujun728.aoplog.compoent"})
public class LogAutoConfig {
}
