package com.jun.plugin.oss.config;

import com.jun.plugin.oss.properties.FileServerProperties;
import com.jun.plugin.oss.template.FdfsTemplate;
import com.jun.plugin.oss.template.S3Template;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @author zlt
 * @date 2021/2/13
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class OssAutoConfigure {

}
