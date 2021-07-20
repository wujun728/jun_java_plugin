package com.jun.plugin.sharding.jdbc.mybatisplus.sharding;

import io.shardingjdbc.core.yaml.sharding.YamlShardingRuleConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Sharding rule configuration properties.
 *
 * @author Wujun
 */
@ConfigurationProperties(prefix = "sharding.jdbc.config.sharding")
public class SpringBootShardingRuleConfigurationProperties extends YamlShardingRuleConfiguration {
}
