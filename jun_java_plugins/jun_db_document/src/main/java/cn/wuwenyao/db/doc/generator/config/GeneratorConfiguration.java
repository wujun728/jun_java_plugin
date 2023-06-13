package cn.wuwenyao.db.doc.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.wuwenyao.db.doc.generator.dao.DbInfoDao;
import cn.wuwenyao.db.doc.generator.service.GeneratorService;

/***
 * 生成器配置
 * 
 * @author wwy shiqiyue.github.com
 *
 */
@Configuration
public class GeneratorConfiguration {
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Bean
	public DbInfoDao dbInfoDao() throws InstantiationException, IllegalAccessException {
		DbInfoDao dbInfoDao = (DbInfoDao) applicationConfig.getGenerator().getDbtype().getDbInfoDaoImpl().newInstance();
		dbInfoDao.setJdbcTemplate(jdbcTemplate);
		dbInfoDao.setApplicationConfig(applicationConfig);
		return dbInfoDao;
	}
	
	@Bean
	public GeneratorService generatorService(DbInfoDao dbInfoDao)
			throws InstantiationException, IllegalAccessException {
		GeneratorService generatorService = (GeneratorService) applicationConfig.getGenerator().getTargetFileType()
				.getGeneratorServiceImpl().newInstance();
		generatorService.setDbInfoDao(dbInfoDao);
		generatorService.setGeneratorConfig(applicationConfig.getGenerator());
		return generatorService;
	}
	
}
