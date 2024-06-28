package io.github.wujun728.snakerflow;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "workflows")
public class WorkflowsConfig {
	
    public static List<Map<String, String>> list;   //static 才能拿配置值

    public static List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
    	WorkflowsConfig.list = list;
    }

}
