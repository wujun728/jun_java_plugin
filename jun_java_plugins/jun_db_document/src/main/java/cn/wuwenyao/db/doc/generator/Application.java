package cn.wuwenyao.db.doc.generator;

import cn.wuwenyao.db.doc.generator.service.GeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cn.wuwenyao.db.doc.generator.config.ApplicationConfig;

import static org.slf4j.LoggerFactory.getLogger;

/***
 * 应用启动
 * 
 * @author wwy
 *
 */
@EnableConfigurationProperties({ ApplicationConfig.class })
@SpringBootApplication
public class Application implements CommandLineRunner {

	private GeneratorService generatorService;

	private static final Logger logger = getLogger(Application.class);

	public Application(GeneratorService generatorService) {
		this.generatorService = generatorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 生成doc
		try {
			generatorService.generate();
		} catch (Exception e) {
			logger.error("生成数据库文档错误", e);
			return;
		}
		logger.info("生成数据库文档成功");
	}
}
