package cn.wuwenyao.db.doc.generator.service;

import cn.wuwenyao.db.doc.generator.config.GeneratorConfig;
import cn.wuwenyao.db.doc.generator.dao.DbInfoDao;

/***
 * 生成文件-服务
 * 
 * @author wwy shiqiyue.github.com
 *
 */
public interface GeneratorService {


	/***
	 * 生成
	 * @throws Exception
	 */
	void generate() throws Exception;
	
	/***
	 * 生成数据库文档
	 * 
	 * @throws Exception
	 */
	void generateDbDoc() throws Exception;
	
	/***
	 * 设置数据库信息Dao
	 * 
	 * @param dbInfoDao
	 */
	public void setDbInfoDao(DbInfoDao dbInfoDao);
	
	/***
	 * 设置配置信息
	 * 
	 * @param generatorConfig
	 */
	public void setGeneratorConfig(GeneratorConfig generatorConfig);
	
}