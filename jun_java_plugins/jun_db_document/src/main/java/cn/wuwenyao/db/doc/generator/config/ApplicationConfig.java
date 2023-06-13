package cn.wuwenyao.db.doc.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/***
 * 应用配置信息
 * 
 * @author wwy shiqiyue.github.com
 *
 */
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {
	
	@NestedConfigurationProperty
	private GeneratorConfig generator = new GeneratorConfig();
	
	public GeneratorConfig getGenerator() {
		return generator;
	}
	
	public void setGenerator(GeneratorConfig generator) {
		this.generator = generator;
	}
}
