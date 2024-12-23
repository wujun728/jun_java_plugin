package io.github.wujun728.admin.db.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("db")
@Data
public class DbConfig {
    private String schema;
    private String manageSchema;
    private String type;
}
