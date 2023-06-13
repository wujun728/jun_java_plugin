package cn.wuwenyao.db.doc.generator.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import cn.wuwenyao.db.doc.generator.utils.FreemarkerUtils;
import freemarker.template.Template;

/***
 * 生成文档服务,word格式
 * <p>
 * 格式难以控制，不再支持，版本1.5移除
 * </p>
 * 
 * @deprecated
 * 
 * 
 * @author wwy
 *
 */
public final class WordGeneratorServiceImpl extends AbstractGeneratorServiceImpl {
	
	/** 模板名称 */
	private String templateFileName = "wordTemplate.ftl";
	
	@Override
	public void generateDbDoc() throws Exception {
		String databaseName = dbInfoDao.databaseName();
		List<TableInfo> tableInfos = dbInfoDao.tableInfoList();
		// 获取模板
		templateFileName = StringUtils.defaultIfBlank(generatorConfig.getTemplateFilePath(), templateFileName);
		Template template = FreemarkerUtils.getTemplate(templateFileName);
		File file = this.createFile("doc");
		Map<String, Object> map = new HashMap<>(2);
		map.put("tableInfos", tableInfos);
		map.put("databaseName", databaseName);
		// 根据模板生成文件
		template.process(map, new FileWriter(file));
	}
	
}
