package com.caland.common.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.caland.common.data.DataMng;
import com.caland.common.data.Table;
import com.caland.common.junit.AbstractSpringJunitTest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ModuleGenerator extends AbstractSpringJunitTest{

	
	public static final String entity_p = "com.caland.core.bean";
	public static final String query_p = "com.caland.core.query";
	public static final String entity_xml_p = "ibatis";
	public static final String sqlmap_config_xml = "";
	
	public static final String action_p = "com.caland.core.action";
	public static final String dao_p = "com.caland.core.dao";
	public static final String dao_impl_p = "com.caland.core.dao.impl";
	public static final String service_p = "com.caland.core.service";
	public static final String service_impl_p = "com.caland.core.service.impl";
	
	public static final String template_dir = "com.caland.common.template";
	public static final String dao_tpl = "dao.txt";
	public static final String dao_impl_tpl = "dao_impl.txt";
	public static final String service_tpl = "service.txt";
	public static final String service_impl_tpl = "service_impl.txt";
	public static final String action_tpl = "action.txt";
	public static final String entity_tpl = "entity.txt";
	public static final String query_tpl = "query.txt";
	public static final String entity_xml_tpl = "entity-sqlmap.txt";
	public static final String sqlmap_config_tpl = "sqlmap-config.txt";
	//分库
	public static final String sharding_rules_on_namespace_p = "dbRule";
	public static final String hash_function_p = "com.caland.core.dao.router";
	public static final String sharding_rules_on_namespace_tpl = "sharding-rules-on-namespace.txt";
	public static final String hash_function_tpl = "hash-function.txt";
	

	
	public static final boolean is_action = true;
	public static final boolean is_service = true;
	public static final boolean is_dao = true;
	public static final boolean is_page = false;
	
	
	public static final String SPT = File.separator;

	public static final String ENCODING = "UTF-8";

	Map<String, Object> data = new HashMap<String, Object>();
	private void loadData(String entity) { 
		String Entity = upperCaseFirst(entity);
		data.put("Entity", Entity);
		data.put("entity", entity);
		data.put("entity_p", entity_p);
		data.put("query_p", query_p);
		data.put("entity_xml_p", entity_xml_p);
		data.put("action_p", action_p);
		data.put("dao_p", dao_p);
		data.put("dao_impl_p", dao_impl_p);
		data.put("service_p", service_p);
		data.put("service_impl_p", service_impl_p);
		data.put("template_dir", template_dir);
	}
	/**
	 * 生成
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void generatorAll() throws TemplateException, IOException{
		//bean
		f = new File(getFilePath((String) data.get("entity_p"),data.get("Entity") + ".java"));
		tpl = converString((String) data.get("template_dir")) + SPT + entity_tpl;
		index(tpl,f);
		//query
		f = new File(getFilePath((String) data.get("query_p"),data.get("Entity") + "Query.java"));
		tpl = converString((String) data.get("template_dir")) + SPT + query_tpl;
		index(tpl,f);
		//entity_xml
		f = new File(getFilePath((String) data.get("entity_xml_p"),data.get("entity") + "-sqlmap.xml"));
		tpl = converString((String) data.get("template_dir")) + SPT + entity_xml_tpl;
		index(tpl,f);
		//action
		f = new File(getFilePath((String) data.get("action_p"),data.get("Entity") + "Act.java"));
		tpl = converString((String) data.get("template_dir")) + SPT + action_tpl;
		index(tpl,f);
		//service
		f = new File(getFilePath((String) data.get("service_p"),data.get("Entity") + "Service.java"));
		tpl = converString((String) data.get("template_dir")) + SPT + service_tpl;
		index(tpl,f);
		f = new File(getFilePath((String) data.get("service_impl_p"),data.get("Entity") + "ServiceImpl.java"));
		tpl = converString((String) data.get("template_dir")) + SPT + service_impl_tpl;
		index(tpl,f);
		//Dao
		f = new File(getFilePath((String) data.get("dao_p"),data.get("Entity") + "Dao.java"));
		tpl = converString((String) data.get("template_dir")) + SPT + dao_tpl;
		index(tpl,f);
//		f = new File(getFilePath((String) data.get("dao_impl_p"),data.get("Entity") + "DaoImpl.java"));
//		tpl = converString((String) data.get("template_dir")) + SPT + dao_impl_tpl;
//		index(tpl,f);
	}
	/**
	 * 生成
	 * @param tpl
	 * @param f
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void index(String tpl,File f) throws TemplateException, IOException{
		File parent = f.getParentFile();
		if (!parent.exists()) {
			f.mkdirs();
		}
		Writer out = null;
		try {
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			out = new OutputStreamWriter(new FileOutputStream(f), ENCODING);
			Template template = conf.getTemplate(tpl);
			template.process(data, out);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		
	}
	/**
	 * 项目classpath相对路径
	 * @param packageName
	 * @param name
	 * @return
	 */
	private String getFilePath(String packageName, String name) {
		String path = converString(packageName);
		return "src/" + path + "/" + name;
	}
	/**
	 * \\.  换 /
	 * @param s
	 * @return
	 */
	private String converString(String s){
		return s.replaceAll("\\.", "/");
	}
	/**
	 * 头字母大写
	 * @param s
	 * @return
	 */
	private String upperCaseFirst(String s){
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	/**
	 * 去掉下划线
	 * @param s
	 * @return
	 */
	private String replaceLine(String s){
		return s.replaceAll("_", "");
	}
	/**
	 * 去掉表头
	 * @return
	 */
	private String trimFirst(String tableName){
		String[] splits = tableName.split("_");
		if("psm".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(4);
		}else if("mm".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(3);
		}else if("dm".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(3);
		}else if("pm".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(3);
		}else if("sm".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(3);
		}else if("tm".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(3);
		}else if("um".equalsIgnoreCase(splits[0])){
			tableName = tableName.substring(3);
		}
		return tableName;
	}
	File f = null;
	String tpl = null;
	/**
	 * 数据库 指定表
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void start() throws TemplateException, IOException {
		String[] tableNames = {};
		List<String> entitys = new ArrayList<String>();
		if(null != tableNames && tableNames.length > 0){
			//数据库 指定表
			for(String tablename : tableNames){
				Table t = dataMng.findTable(tablename);
				String entity = replaceLine((trimFirst(t.getName())));
				data.put("table", t.getName());
				data.put("fields", dataMng.listFields(t.getName()));
				loadData(entity);
				generatorAll();
				entitys.add(entity);
			}
		}else{
			//数据库所有表
			List<Table> tabels = dataMng.listTabels();
			for(Table t : tabels){
				String entity = replaceLine((trimFirst(t.getName())));
				data.put("table", t.getName());
				data.put("fields", dataMng.listFields(t.getName()));
				loadData(entity);
				generatorAll();
				entitys.add(entity);
			}
		}
		data.put("entitys", entitys);
		//sqlmap-config
		f = new File(getFilePath(sqlmap_config_xml,"sqlmap-config.xml"));
		tpl = converString(template_dir) + SPT + sqlmap_config_tpl;
		index(tpl,f);
		//sharding_rules_on_namespace
		f = new File(getFilePath(sharding_rules_on_namespace_p,"sharding-rules-on-namespace.xml"));
		tpl = converString(template_dir) + SPT + sharding_rules_on_namespace_tpl;
		index(tpl,f);
		//hash_function
		f = new File(getFilePath(hash_function_p,"HashFunction.java"));
		tpl = converString(template_dir) + SPT + hash_function_tpl;
		index(tpl,f);
	}
	@Autowired
	private DataMng dataMng;
	private Configuration conf;

	@Autowired
	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}
	/**
	 * 从数据库表生成javaBean , action ,service ,dao ,sqlMap,sqlmap-config
	 * @throws TemplateException
	 * @throws IOException
	 */
	@Test
	public void generator() throws TemplateException, IOException{
		start();
	}
}
