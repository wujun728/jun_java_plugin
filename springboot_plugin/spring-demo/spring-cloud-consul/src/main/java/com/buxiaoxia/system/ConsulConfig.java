package com.buxiaoxia.system;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by xw on 2017/2/23.
 * 2017-02-23 12:00
 */
@Data
@ConfigurationProperties("test")
public class ConsulConfig {

	private String conf1;

	private String conf2;

	private String conf3;

}
