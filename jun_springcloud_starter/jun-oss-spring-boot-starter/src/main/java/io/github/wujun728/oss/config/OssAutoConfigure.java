package io.github.wujun728.oss.config;

import io.github.wujun728.oss.properties.FileServerProperties;
import io.github.wujun728.oss.template.FdfsTemplate;
import io.github.wujun728.oss.template.S3Template;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class OssAutoConfigure {

}
