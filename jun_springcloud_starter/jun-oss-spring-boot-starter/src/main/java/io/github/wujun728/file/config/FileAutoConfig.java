package io.github.wujun728.file.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wujun
 * @date 2021/3/19
 */
@Configuration
@ComponentScan(basePackages = "io.github.wujun728.file")
@MapperScan(basePackages = "io.github.wujun728.file.mapper")
public class FileAutoConfig {
}
