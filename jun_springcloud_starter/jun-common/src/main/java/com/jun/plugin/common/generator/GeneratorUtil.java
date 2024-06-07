package com.jun.plugin.common.generator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.*;
import java.util.*;


/**
 * 代码生成器 工具类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Slf4j
public class GeneratorUtil {


	private static final Logger logger = LoggerFactory.getLogger(GeneratorUtil.class);

	private static DataSource dataSource;
	public static Map config; // 配置文件
	public static List<String> templates; // 模板文件
	public static List<String> filePaths; // 生成文件名

    public static void init() {
		if(CollectionUtils.isEmpty(config)){
			//PropertiesUtil.loadProps("config-v2.properties");
			config = new HashMap<>();
			config.put("template_path","D:\\");
			config.put("output_path","D:\\");
			config.put("jdbc.url","");
			config.put("jdbc.username","root");
			config.put("jdbc.password","");
			config.put("jdbc.driver","com.mysql.cj.jdbc.Driver");
			config.put("packageName","com.bjc.lcp.app");
			config.put("userDefaultTemplate","true");
			config.put("authorName","Wujun");
			config.put("isLombok","true");
			config.put("isSwagger","true");
			config.put("isAutoImport","true");
			config.put("isWithPackage","true");
			config.put("isComment","true");
			config.put("className","${className}Controller.subfix.ftl");
			config.put("javaPath","src/main/java");
			config.put("resourcesPath","src/main/resources");
			config.put("tableRemovePrefixes","T_AR,T_BD,T_CD,T_PD,T_CL,T_IP,T_LO,T_RI,T_EV,T_,");
			config.put("rowRemovePrefixes","S_,B_,I_,DT_,TS_,M_,F_,PK_I_N,PK_I_S");
			config.put("java.sql.Timestamp","Date");
			config.put("java.sql.Date","Date");
			config.put("java.sql.Time","Date");
			config.put("java.util.Date","Date");
			config.put("java.lang.Byte","Integer");
			config.put("java.lang.Short","Integer");
			config.put("java.lang.Integer","Integer");
			config.put("java.lang.Long","Long");
			config.put("java.lang.String","String");
			config.put("java.math.BigDecimal","java.math.BigDecimal");
			//props.putAll(PropertiesUtil.getAllProperty());
		}
	}
    public static String getConfig(String key) {
		String val = MapUtil.getStr(config,key);
		return val;
	}
    public static List<String> getFilePaths(List<String> templates, ClassInfo classInfo) {
        List<String> filePaths = new ArrayList<>();
		for(String template : templates){
			String path_tmep = FileNameUtil.getName(template).replace(".ftl","");
			String filename_resc = path_tmep;
			//String package3Path = String.format("/%s/", path1.contains(".") ? path1.replaceAll("\\.", "/") : path1);
			if(template.contains(".java")){
				String path1 = path_tmep.substring(0,path_tmep.lastIndexOf("."));
				String filename_tmep = upperCaseFirstWordV2(path1);
				String packageName = getConfig("packageName")+"."+path1;
				String package2Path = String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
				filePaths.add(getConfig("output_path") +File.separator+ getConfig("javaPath") + package2Path + classInfo.getClassName() + filename_tmep + ".java");
			}else{
				filePaths.add(getConfig("output_path") +File.separator+ getConfig("resourcesPath") + File.separator+classInfo.getClassName() +File.separator+ filename_resc );
			}
		}
        return filePaths;
    }

//	public static void getFile(String path, List<Map<String, Object>> list) {
//		File file = new File(path);
//		File[] array = file.listFiles();
//		for (int i = 0; i < array.length; i++) {
//			if (array[i].isFile()) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				// only take file name
//				// System.out.println("^^^^^" + array[i].getName());
//				// take file path and name
//				// System.out.println("*****" + array[i].getPath());
//				map.put(array[i].getName(), array[i].getPath());
//				list.add(map);
//			} else if (array[i].isDirectory()) {
//				getFile(array[i].getPath(), list);
//			}
//		}
//	}

	public static String replace_(String str) {
		// 根据下划线分割
		String[] split = str.split("_");
		// 循环组装
		String result = split[0];
		if (split.length > 1) {
			for (int i = 1; i < split.length; i++) {
				result += firstUpper(split[i]);
			}
		}
		return result;
	}

	public static String firstUpper(String str) {
		// log.info("str:"+str+",str.length="+str.length());
		if (!org.springframework.util.StringUtils.isEmpty(str)) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str;
		}
	}

//	public static String firstLower(String str) {
//		return str.substring(0, 1).toLowerCase() + str.substring(1);
//	}

	public static String replaceTabblePreStr(String str) {
//		str = str.toLowerCase().replaceFirst("tab_", "").replaceFirst("tb_", "").replaceFirst("t_", "");
		for (String x : getConfig("tableRemovePrefixes").split(",")) {
			if(str.startsWith(x.toLowerCase())){
				str = str.replaceFirst(x.toLowerCase(), "");
			}
		}
		return str;
	}

	public static String replaceRowPreStr(String str) {
//		str = str.toLowerCase().replaceFirst("tab_", "").replaceFirst("tb_", "").replaceFirst("t_", "");
		for (String x : getConfig("rowRemovePrefixes").split(",")) {
			str = str.replaceFirst(x.toLowerCase(), "");
		}
		return str;
	}

	public static String simpleName(String type) {
		return type.replace("java.lang.", "").replaceFirst("java.util.", "");
	}

//	public static String upperCaseFirstWord(String str) {
//		return str.substring(0, 1).toUpperCase() + str.substring(1);
//	}
	public static String upperCaseFirstWordV2(String str) {
		if(str!=null && str.length()>0){
			if(str.contains(".")){
				String strs[] = str.split("\\.");
				String temp = "";
				for(String strtmp : strs){
					temp += strtmp.substring(0, 1).toUpperCase() + strtmp.substring(1);
				}
				return temp;
			}else{
				return str.substring(0, 1).toUpperCase() + str.substring(1);
			}
		}
		return str;
	}

	public static String getType(int value) {
		switch (value) {
		case -6:
			return "java.lang.Integer";
		case 5:
			return "java.lang.Integer";
		case 4:
			return "java.lang.Integer";
		case -5:
			return "java.lang.Long";
		case 6:
			return "java.lang.Float";
		case 8:
			return "java.lang.Double";
		case 1:
			return "java.lang.String";
		case 12:
			return "java.lang.String";
		case -1:
			return "java.lang.String";
		case 91:
			return "java.util.Date";
		case 92:
			return "java.util.Date";
		case 93:
			return "java.util.Date";
		case 16:
			return "java.lang.Boolean";
		default:
			return "java.lang.String";
		}
	}
	
	
	public static void processTemplatesFileWriter(ClassInfo classInfo, Map<String, Object> datas, List<String> templates) throws IOException, TemplateException {
		for(int i = 0 ; i < templates.size() ; i++) {
			if(CollectionUtils.isEmpty(filePaths)){
				GeneratorUtil.processFile(templates.get(i), datas, GeneratorUtil.getFilePaths(templates,classInfo).get(i));
			}else{
				GeneratorUtil.processFile(templates.get(i), datas, filePaths.get(i));
			}
		}
	}

	public static void processFile(String templateName, Map<String, Object> data, String filePath)
			throws IOException, TemplateException {
		Template template = getConfiguration().getTemplate(templateName);
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		template.process(data, new FileWriter(file));
		System.out.println(filePath + " 生成成功");
	}

	/***
	 * 模板构建，StringWriter 返回构建后的文本，不生成文件
	 */
	public static String processString(String templateName, Map<String, Object> params)
			throws IOException, TemplateException {
		Template template = getConfiguration().getTemplate(templateName);
		StringWriter result = new StringWriter();
		template.process(params, result);
		String htmlText = result.toString();
		return htmlText;
	}

	@Deprecated
	public static String genTemplateStr(Map<String, Object> params,String templateContent)
			throws IOException, TemplateException {
		return genTemplateStr(params,"temp",templateContent);
	}
	/**
	 *  解析模板
	 * @param params 内容
	 * @param templateName 参数
	 * @param templateContent 参数
	 * @return
	 */
	public static String genTemplateStr(Map<String, Object> params,String templateName,String templateContent)
			throws IOException, TemplateException {
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		Template template = new Template(templateName, new StringReader(templateContent));
		StringWriter result = new StringWriter();
		template.process(params, result);
		String htmlText = result.toString();
		return htmlText;
	}

	private static freemarker.template.Configuration getConfiguration() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_23);
		if(getConfig("userDefaultTemplate").equalsIgnoreCase("false")){
//			getProp("output_path") +File.separator+
			cfg.setDirectoryForTemplateLoading(new File(getConfig("template_path")));
		}else{
			cfg.setClassForTemplateLoading(GeneratorUtil.class,"/");
		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setNumberFormat("#");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}


	/***
	 * 模板构建，输出源码字符串
	 */
	public static Map<String, String> processTemplatesStringWriter(Map<String, Object> datas, List<String> templates)
			throws IOException, TemplateException {
		Map<String, String> result = new HashMap<String, String>();
		for(int i = 0 ; i < templates.size() ; i++) {
			GeneratorUtil.processString(templates.get(i), datas );
			result.put(templates.get(i), GeneratorUtil.processString(templates.get(i), datas));
		}
		return result;
	}


	public static ClassInfo getClassInfo(Table table) {
		// V1 初始化数据及对象 模板V1 field List
		List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
		for (Column column : table.getColumns()) {
			// V1 初始化数据及对象
			String remarks = column.getComment();// cloumnsSet.getString("REMARKS");// 列的描述
			String columnName = column.getName();// cloumnsSet.getString("COLUMN_NAME"); // 获取列名
			String javaType = GeneratorUtil.getType(column.getType()/*cloumnsSet.getInt("DATA_TYPE")*/);// 获取类型，并转成JavaType
			String columnType = column.getTypeName();// 获取类型，并转成JavaType
			long COLUMN_SIZE = column.getSize();// cloumnsSet.getInt("COLUMN_SIZE");// 获取
			String COLUMN_DEF = column.getColumnDef();// cloumnsSet.getString("COLUMN_DEF");// 获取
			Boolean nullable = column.isNullable();// cloumnsSet.getInt("NULLABLE");// 获取
			String propertyName = GeneratorUtil.replace_(GeneratorUtil.replaceRowPreStr(columnName));// 处理列名，驼峰
			Boolean isPk = column.isPk();

			// V1 初始化数据及对象
			FieldInfo fieldInfo = new FieldInfo();
			fieldInfo.setColumnName(columnName);
			fieldInfo.setColumnType(columnType);
			fieldInfo.setFieldName(propertyName);
			fieldInfo.setFieldClass(GeneratorUtil.simpleName(javaType));
			fieldInfo.setFieldComment(remarks);
			fieldInfo.setColumnSize(COLUMN_SIZE);
			fieldInfo.setNullable(nullable);
			fieldInfo.setFieldType(javaType);
			fieldInfo.setIsPrimaryKey(isPk);
			fieldList.add(fieldInfo);
		}
		if (fieldList != null && fieldList.size() > 0) {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setTableName(table.getTableName());
			String className = GeneratorUtil.replace_(GeneratorUtil.replaceTabblePreStr(table.getTableName())); // 名字操作,去掉tab_,tb_，去掉_并转驼峰
			String classNameFirstUpper = GeneratorUtil.firstUpper(className); // 大写对象
			classInfo.setClassName(classNameFirstUpper);
			if(table.getComment().contains("表")){
				classInfo.setClassComment(table.getComment().replace("表",""));
			}else{
				classInfo.setClassComment(table.getComment());
			}
			classInfo.setFieldList(fieldList);
			classInfo.setPkSize(table.getPkNames().size());
			return classInfo;
		}
		return null;
	}


	public static void genTables(List<ClassInfo> classInfos, List<String> templates ) throws Exception {

		if(CollectionUtils.isEmpty(templates)){
			templates = GeneratorUtil.templates;
			if(CollectionUtils.isEmpty(templates)){
				templates = GeneratorUtil.templates;
				logger.error("代码生成模板未初始化，请初始化【templates】");
			}
		}
		List<String> finalTemplates = templates;
		classInfos.forEach(classInfo -> {
			Map<String, Object> datas = new HashMap<String, Object>();
			datas.put("classInfo", classInfo);
			config.forEach((k, v)->{
				if(String.valueOf(v).equalsIgnoreCase("true")||String.valueOf(v).equalsIgnoreCase("false")){
					datas.put(String.valueOf(k), Boolean.valueOf(String.valueOf(v)));
				}else{
					datas.put(String.valueOf(k), v);
				}
			});
			datas.put("package", getConfig("package"));
			datas.put("author", getConfig("author"));
			datas.put("email", getConfig("email"));
			datas.put("datetime", DateUtil.now());
			datas.put("identity", IdUtil.getSnowflakeNextIdStr());
			datas.put("addId", IdUtil.getSnowflakeNextIdStr());
			datas.put("updateId", IdUtil.getSnowflakeNextIdStr());
			datas.put("deleteId", IdUtil.getSnowflakeNextIdStr());
			datas.put("selectId", IdUtil.getSnowflakeNextIdStr());

			Map<String, String> result = new HashMap<String, String>();
			try {
				result = GeneratorUtil.processTemplatesStringWriter(datas, finalTemplates);
				GeneratorUtil.processTemplatesFileWriter(classInfo, datas, finalTemplates);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			// 计算,生成代码行数
			int lineNum = 0;
			for (Map.Entry<String, String> item : result.entrySet()) {
				if (item.getValue() != null) {
					lineNum += StringUtils.countMatches(item.getValue(), "\n");
				}
			}
			logger.info("生成代码行数：{}", lineNum);
		});
		if (CollectionUtils.isEmpty(classInfos)) {
			logger.error("找不到当前表的元数据classInfos.size()：{}", classInfos.size());
		}
	}



	public static List<ClassInfo> getClassInfos(List<String> tableNames) {
		DataSource ds = getDruidDataSource();
		List<ClassInfo> classInfos = Lists.newArrayList();
		tableNames.stream().forEach(t -> {
			Table table = MetaUtil.getTableMeta(ds, t);
			if(table.getPkNames().size()>0){//没有主键是不生成的
				classInfos.add(GeneratorUtil.getClassInfo(table));
			}else{
				StaticLog.error("表"+table.getTableName()+"没有主键是不生成代码的，至少得一个主键");
			}
		});
		return classInfos;
	}


	private static DataSource getDruidDataSource() {
		if(dataSource == null){
			DruidDataSource ds = new DruidDataSource();
			ds.setDriverClassName(getConfig("jdbc.driver"));
			ds.setUrl(getConfig("jdbc.url"));
			ds.setUsername(getConfig("jdbc.username"));
			ds.setPassword(getConfig("jdbc.password"));
			ds.setRemoveAbandoned(true);
			ds.setRemoveAbandonedTimeout(600);
			ds.setLogAbandoned(true);
//			ds.setBreakAfterAcquireFailure(true);
			ds.setTimeBetweenConnectErrorMillis(60000);//1分钟
//			ds.setConnectionErrorRetryAttempts(0);
			ds.setValidationQuery("SELECT 1");//用来检测连接是否有效
//			ds.setTestOnBorrow(false);//借用连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
//			ds.setTestOnReturn(false);//归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
			//连接空闲时检测，如果连接空闲时间大于timeBetweenEvictionRunsMillis指定的毫秒，执行validationQuery指定的SQL来检测连接是否有效
//			ds.setTestWhileIdle(true);//如果检测失败，则连接将被从池中去除
			ds.setMaxActive(200);
			ds.setInitialSize(5);
			dataSource = ds;
			return ds;
		}
		return dataSource;
	}




	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		GeneratorUtil.dataSource = dataSource;
	}

	public static List<String> getTemplates() {
		return templates;
	}

	public static void setTemplates(List<String> templates) {
		GeneratorUtil.templates = templates;
	}

	public static Map getConfig() {
		return config;
	}

	public static void initConfig(Map props) {
		init();
		GeneratorUtil.config.putAll(props);
	}

	public static void genCode(String tableNames) throws Exception {
		init();
		if( CollectionUtils.isEmpty(templates)){
			config.put("userDefaultTemplate","true");// 未设置模板，使用内置模板
		}
		if(getConfig("userDefaultTemplate").equalsIgnoreCase("true")  ){
			templates = Lists.newArrayList();
			// ************************************************************************************
			templates.add("/templates/mybatis-plus-single-v2/controller.java.ftl");
			templates.add("/templates/mybatis-plus-single-v2/entity.java.ftl");
			templates.add("/templates/mybatis-plus-single-v2/mapper.java.ftl");
			templates.add("/templates/mybatis-plus-single-v2/service.java.ftl");
			templates.add("/templates/mybatis-plus-single-v2/dto.java.ftl");
			templates.add("/templates/mybatis-plus-single-v2/vo.java.ftl");
			templates.add("/templates/mybatis-plus-single-v2/service.impl.java.ftl");
		}
		GeneratorUtil.genCode(Arrays.asList(tableNames.split(",")),templates);
	}
	public static void genCode(List<String> tableNames, List<String> templates ) throws Exception {
		init();
		List<ClassInfo> classInfos = GeneratorUtil.getClassInfos(tableNames);
		GeneratorUtil.genTables(classInfos, templates);
	}
	public static void genCode(List<String> tableNames) throws Exception {
		init();
		List<ClassInfo> classInfos = GeneratorUtil.getClassInfos(tableNames);
		GeneratorUtil.genTables(classInfos, templates);
	}


	public static void help() throws Exception {
		StaticLog.info("Step1，代码生成器使用步骤");
		StaticLog.info("Step2，写个mani方法，copy下面步骤的代码");
		StaticLog.info("\tpublic static void main(String[] args) throws Exception {\n" +
				"\t\t//String tables = \"res_basc,res_basc_arg,api_config\";\n" +
				"\t\tMap config = Maps.newHashMap();\n" +
				"\t\tconfig.put(\"authorName\",\"Wujun\");\n" +
				"\t\tconfig.put(\"packageName\",\"com.bjc.lcp.app1\");\n" +
				"\t\tconfig.put(\"template_path\",\"D:\\\\workspace\\\\github\\\\jun_api_service\\\\jun_api_service_online\\\\plugins\\\\generator2\\\\src\\\\main\\\\resources\\\\mybatis-plus-single-v3\");\n" +
				"\t\tconfig.put(\"output_path\",\"D:\\\\workspace\\\\github\\\\jun_api_service\\\\jun_api_service_online\\\\plugins\\\\generator2\");\n" +
				"\t\tconfig.put(\"jdbc.url\",\"jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true\");\n" +
				"\t\tconfig.put(\"jdbc.username\",\"root\");\n" +
				"\t\tconfig.put(\"jdbc.password\",\"\");\n" +
				"\t\tconfig.put(\"jdbc.driver\",\"com.mysql.cj.jdbc.Driver\");\n" +
				"\t\tconfig.put(\"isLombok\",\"true\");\n" +
				"\t\tconfig.put(\"isSwagger\",\"true\");\n" +
				"\t\tconfig.put(\"userDefaultTemplate\",\"true\");\n" +
				"\t\t//GeneratorUtil.setTemplates(getTemplates());\n" +
				"\t\tGeneratorUtil.initConfig(config);\n" +
				"\t\tGeneratorUtil.genCode(\"ext_salgrade\");\n" +
				"\t}");
		StaticLog.info("Step3，以上为使用默认模板生成");
		StaticLog.info("Step4，自定义模板需要设置userDefaultTemplate为false");
		StaticLog.info("Step5，并设置自定义模板清单及路径");
		StaticLog.info("public static List<String> getTemplates() {\n" +
				"\t\tList<String> templates = Lists.newArrayList();\n" +
				"\t\ttemplates.add(\"controller.java.ftl\");\n" +
				"\t\ttemplates.add(\"entity.java.ftl\");\n" +
				"\t\ttemplates.add(\"mapper.java.ftl\");\n" +
				"\t\ttemplates.add(\"service.java.ftl\");\n" +
				"\t\ttemplates.add(\"dto.java.ftl\");\n" +
				"\t\ttemplates.add(\"vo.java.ftl\");\n" +
				"\t\ttemplates.add(\"service.impl.java.ftl\");\n" +
				"\t\treturn templates;\n" +
				"\t}");
	}

}
