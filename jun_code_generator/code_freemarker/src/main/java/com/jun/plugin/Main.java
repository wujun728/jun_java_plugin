package com.jun.plugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.FileCreator;
import com.jun.plugin.factory.ModuleFactory;
import com.jun.plugin.util.DbUtils;

import freemarker.template.TemplateException;

/**
 * 代码生成执行类
 * @author Wujun
 */
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws ClassNotFoundException, SQLException, TemplateException, IOException {
		new Main().doCreate();
	}

	public void doCreate() throws SQLException, ClassNotFoundException, IOException, TemplateException {
		Config conf = new Config().getConf();
		List<TableInfo> tableInfos = DbUtils.getInstance().getAllTables(conf);
		LOGGER.info("tableInfos ==>" + tableInfos);
		createFile(conf, tableInfos);
	}

	private void createFile(Config conf, List<TableInfo> tableInfos) throws IOException, TemplateException {
		List<String> modules = conf.getModules();
		FileCreator creator = null;
		for (TableInfo tableInfo : tableInfos) {
			LOGGER.info("tableInfo ==>" + tableInfo);
			for (String module : modules) {
				creator = ModuleFactory.create(module, conf, tableInfo);
				creator.createFile(tableInfo);
			}
		}
	}


}
