package org.itkk.udf.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 描述 : 系统配置
 *
 * @author wangkang
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.application.config")
@Validated
@Data
public class ApplicationConfig {

    /**
     * 描述 : 是否输出异常堆栈
     */
    @NotNull
    private boolean outputExceptionStackTrace = false;

    /**
     * 描述 : 编码
     */
    @NotNull
    private String encoding = "UTF-8";

    /**
     * 描述 : 时区
     */
    @NotNull
    private String timeZone = "Asia/Shanghai";

    /**
     * 描述 : 日期格式
     */
    @NotNull
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSSZ";

    /**
     * 描述 : 线程池属性
     */
    private ThreadPoolProperties threadPool;

    /**
     * 描述 : 如有大文件通过restTemplate传输,请设置为false
     */
    @NotNull
    private Boolean bufferRequestBody = true;

    /**
     * 描述 : 线程池配置
     *
     * @author Administrator
     */
    @Data
    public static class ThreadPoolProperties {

        /**
         * 描述 : 默认线程池大小(默认:1)
         */
        private Integer poolSize = 1;

        /**
         * 描述 : 线程优先级
         */
        private Integer threadPriority = Thread.NORM_PRIORITY;

        /**
         * 描述 : 线程名称
         */
        private String threadNamePrefix = "defaultThreadPool";

    }

}
