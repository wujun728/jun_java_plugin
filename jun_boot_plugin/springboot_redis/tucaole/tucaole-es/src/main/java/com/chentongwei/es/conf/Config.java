package com.chentongwei.es.conf;

import java.io.Serializable;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: ES的基本属性配置
 */
@ConfigurationProperties(prefix = "es")
@PropertySource({"classpath:es-config.properties"})
@Component
@Data
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	//集群名称
	private String clusterName;
	//ip
	private String ip;
	//端口号
	private Integer port;
}
