package cn.centychen.springboot.starter.xxljob.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create by cent at 2019-05-08.
 */
@Data
@ConfigurationProperties(prefix = "xxl-job")
public class XxlJobProperties {
    /**
     * xxl job admin properties.
     */
    private AdminProperties admin = new AdminProperties();
    /**
     * xxl job executor properties.
     */
    private ExecutorProperties executor = new ExecutorProperties();


    /**
     * xxl-job admin properties.
     */
    @Data
    static public class AdminProperties {
        /**
         * xxl job admin address.
         */
        private String adminAddresses = "http://127.0.0.1:8080/xxl-job-admin";
    }

    /**
     * xxl-job executor properties.
     */
    @Data
    static public class ExecutorProperties {
        /**
         * xxl job registry name.
         */
        private String appName = "xxl-job-executor";
        /**
         * xxl job registry ip.
         */
        private String ip;
        /**
         * xxl job registry port.
         */
        private Integer port = 9999;
        /**
         * xxl job admin registry access token.
         */
        private String accessToken;
        /**
         * xxl job log files path.
         */
        private String logPath = "logs/applogs/xxl-job/jobhandler";
        /**
         * xxl job log files retention days.
         */
        private Integer logRetentionDays = 30;
    }
}
