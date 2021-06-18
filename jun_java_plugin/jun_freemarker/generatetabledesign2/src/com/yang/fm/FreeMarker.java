package com.yang.fm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yang.entity.Table;
import com.yang.sql.SelectTableSql;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成表设计
 * @author shichenyang89@gmail.com
 * 
 */
public class FreeMarker {
	SimpleDateFormat format=new SimpleDateFormat();

	//日志对象
	private static Logger logger=Logger.getLogger(FreeMarker.class);
	private Configuration configuration = null;

	public static void main(String[] args) throws Exception {
		logger.debug("开始生成表");
		new FreeMarker().create();
		logger.debug("生成结束");
	}

	@SuppressWarnings("deprecation")
	public FreeMarker() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	/**
	 * 生成表设计
	 * @throws Exception
	 */
	public void create() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		SelectTableSql updateSql = new SelectTableSql();
		//第一步，加载模板
		configuration.setClassForTemplateLoading(this.getClass(), "/");
		Template template = configuration.getTemplate("info.ftl");
		//设置文件输出位置
		File outFile = new File("D:/outFile.doc");

		//如果文件输出位置不存在，先进行创建
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}

		//增加输出流
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile)));
		//第二步，获取数据库数据
		List<Table> tables = updateSql.getSchema();
		map.put("tables", tables);
		//最后一步，调用freemaker引擎方法
		template.process(map, out);
	}

}
