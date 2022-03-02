package com.jun.plugin.codegenerator.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.codegenerator.admin.core.model.ClassInfo;
import com.jun.plugin.codegenerator.admin.core.model.FieldInfo;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 代码生成器，根据DatabaseMetaData及数据表名称生成对应的Model、Mapper、Service、Controller简化基础代码逻辑开发。
 * 
 * @author Wujun
 */
public class CodeGeneratorUtils {
	private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorUtils.class);

	private static final String PROJECT_PATH = System.getProperty("user.dir");// 项目在硬盘上的基础路径，项目路径
	private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/main/resources/templates";// 模板位置
	private static final String JAVA_PATH = "/src/main/java"; // java文件路径
	private static final String RESOURCES_PATH = "/src/main/resources";// 资源文件路径
	private static Properties props = new Properties(); // 配置文件
	static {
		try {
			InputStream is = CodeGeneratorUtils.class.getClassLoader().getResourceAsStream("config.properties");
			props.load(is);
			Class.forName(props.getProperty("driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String PACKAGE_POJO = props.getProperty("pojoPackage");
	public static String PACKAGE_MAPPER = props.getProperty("mapperPackage");
	public static String PACKAGE_BASE = props.getProperty("basePackage");
	public static String PACKAGE_SERVICE_INTERFACE = props.getProperty("serviceInterfacePackage");
	public static String PACKAGE_SERVICE_INTERFACE_IMPL = props.getProperty("serviceInterfaceImplPackage");
	public static String PACKAGE_CONTROLLER = props.getProperty("controllerPackage");
	public static String PACKAGE_FEIGN = props.getProperty("feignPackage");
	public static String UNAME = props.getProperty("uname");
	public static Boolean SWAGGER = Boolean.valueOf(props.getProperty("enableSwagger"));
	public static String SERVICENAME = props.getProperty("serviceName");
	public static String SWAGGERUI_PATH = props.getProperty("swaggeruipath");
	public static String TEMPLATE_PATH = CodeGeneratorUtils.class.getClassLoader().getResource("").getPath()
			.replace("/target/classes/", "") + "/src/main/resources/" + props.getProperty("template_path");
	public static String TEMPLATE_NAME = props.getProperty("template_path");
	public static String TABLEREMOVEPREFIXES = props.getProperty("tableRemovePrefixes");
	public static String ROWREMOVEPREFIXES = props.getProperty("rowRemovePrefixes");
	public static String SKIPTABLE = props.getProperty("skipTable");
	public static String INCLUETABLES = props.getProperty("inclueTables");

	public static void main(String[] args) throws Exception {
		genAllTeamplaters();
	}

	public static void genAllTeamplaters() throws Exception {
		List<ClassInfo> classInfos = CodeGeneratorUtils.processMetadataClassInfo(null);
		classInfos.forEach(classInfo -> {
			Map<String, Object> datas = new HashMap<String, Object>();
			datas.put("classInfo", classInfo);
			Map<String, String> result = new HashMap<String, String>();
			try {
				// genProcessStringWriter(datas, result);
				datas.put("packageController", "com.jun.plugin.biz.controller");
				datas.put("packageService", "com.jun.plugin.biz.service");
				datas.put("packageServiceImpl", "com.jun.plugin.biz.service.impl");
				datas.put("packageDao", "com.jun.plugin.biz.dao");
				datas.put("packageMybatisXML", "com.jun.plugin.biz.model");
				datas.put("packageModel", "com.jun.plugin.biz.model");

				genCRUD(classInfo, datas);
//				genJdbcTemplate(classInfo, datas);

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

	}

	public static void genCRUD(ClassInfo classInfo, Map<String, Object> datas) throws IOException, TemplateException {
		String filePath;
		// 生成 controller.ftl
		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.controller") + classInfo.getClassName()
				+ "Controller.java";
		CodeGeneratorUtils.processFile("code-generator/controller.ftl", datas, filePath);

		// 生成 service.ftl
		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.service") + classInfo.getClassName()
				+ "Service.java";
		CodeGeneratorUtils.processFile("code-generator/service.ftl", datas, filePath);

		// 生成 service_impl.ftl
		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.service.impl") + classInfo.getClassName()
				+ "ServiceImpl.java";
		CodeGeneratorUtils.processFile("code-generator/service_impl.ftl", datas, filePath);

		// 生成 dao.ftl
		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.dao") + classInfo.getClassName()
				+ "Dao.java";
		CodeGeneratorUtils.processFile("code-generator/dao.ftl", datas, filePath);

		// 生成 mybatis.ftl
		filePath = PROJECT_PATH + RESOURCES_PATH + "/mybatis/" + classInfo.getClassName() + ".xml";
		CodeGeneratorUtils.processFile("code-generator/mybatis.ftl", datas, filePath);

		// 生成 model.ftl
		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.model") + classInfo.getClassName()
				+ ".java";
		CodeGeneratorUtils.processFile("code-generator/model.ftl", datas, filePath);
	}

	private static void genJdbcTemplate(ClassInfo classInfo, Map<String, Object> datas)
			throws IOException, TemplateException {
		String filePath;
		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.jtdao") + classInfo.getClassName()
				+ "DAO.java";
		CodeGeneratorUtils.processFile("code-generator/jdbc-template/jtdao.ftl", datas, filePath);

		filePath = PROJECT_PATH + JAVA_PATH + package2Path("com.jun.plugin.biz.jtdao.impl") + classInfo.getClassName()
				+ "DaoImpl.java";
		CodeGeneratorUtils.processFile("code-generator/jdbc-template/jtdaoimpl.ftl", datas, filePath);
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

	public static String processTemplateIntoString(Template template, Object model)
			throws IOException, TemplateException {

		StringWriter result = new StringWriter();
		template.process(model, result);
		return result.toString();
	}

	/***
	 * 模板构建，StringWriter 返回构建后的文本，不生成文件
	 */
	public static String processString(String templateName, Map<String, Object> params)
			throws IOException, TemplateException {
		Template template = getConfiguration().getTemplate(templateName);
		String htmlText = processTemplateIntoString(template, params);
		return htmlText;
	}

	private static freemarker.template.Configuration getConfiguration() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_23);
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private static String package2Path(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}

	/***
	 * 模板构建，
	 */
	public static void genProcessStringWriter(Map<String, Object> datas, Map<String, String> result)
			throws IOException, TemplateException {
		result.put("controller_code", CodeGeneratorUtils.processString("code-generator/controller.ftl", datas));
		result.put("service_code", CodeGeneratorUtils.processString("code-generator/service.ftl", datas));
		result.put("service_impl_code", CodeGeneratorUtils.processString("code-generator/service_impl.ftl", datas));
		result.put("dao_code", CodeGeneratorUtils.processString("code-generator/dao.ftl", datas));
		result.put("mybatis_code", CodeGeneratorUtils.processString("code-generator/mybatis.ftl", datas));
		result.put("model_code", CodeGeneratorUtils.processString("code-generator/model.ftl", datas));
		System.out.println(result);
	}

	public static List<ClassInfo> processMetadataClassInfo(String tablename) throws IOException {
		List<ClassInfo> list = new ArrayList<ClassInfo>();
		CodeGeneratorUtils.builderClassInfo(list);
		return list;
	}

	/***
	 * 数据构建 ClassInfo
	 */
	public static void builderClassInfo(List<ClassInfo> list) {
		try {
			Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("uname"),
					props.getProperty("pwd"));
			DatabaseMetaData metaData = conn.getMetaData();
			String databaseType = metaData.getDatabaseProductName(); // 获取数据库类型：MySQL

			// 针对MySQL数据库进行相关生成操作
			if (databaseType.equals("MySQL")) {
				ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[] { "TABLE" }); // 获取所有表结构
				String database = conn.getCatalog(); // 获取数据库名字

				while (tableResultSet.next()) { // 循环所有表信息

					String tableName = tableResultSet.getString("TABLE_NAME"); // 获取表名
					String table = CodeGeneratorUtils.replace_(CodeGeneratorUtils.replaceTabblePreStr(tableName)); // 名字操作,去掉tab_,tb_，去掉_并转驼峰
					String Table = CodeGeneratorUtils.firstUpper(table); // 获取表名,首字母大写
					String tableComment = tableResultSet.getString("REMARKS"); // 获取表备注
					String className = CodeGeneratorUtils.replace_(CodeGeneratorUtils.replaceTabblePreStr(tableName)); // 名字操作,去掉tab_,tb_，去掉_并转驼峰
					String classNameFirstUpper = CodeGeneratorUtils.firstUpper(className); // 大写对象
//					showTableInfo(tableResultSet); 
					logger.info("当前表名：" + tableName);

					Set<String> typeSet = new HashSet<String>(); // 所有需要导包的类型
					ResultSet cloumnsSet = metaData.getColumns(database, UNAME, tableName, null); // 获取表所有的列
					ResultSet keySet = metaData.getPrimaryKeys(database, UNAME, tableName); // 获取主键
					String key = "", keyType = "";
					while (keySet.next()) {
						key = keySet.getString(4);
					}
					// V1 初始化数据及对象 模板V1 field List
					List<FieldInfo> fieldList = new ArrayList<FieldInfo>();

					while (cloumnsSet.next()) {
						String remarks = cloumnsSet.getString("REMARKS");// 列的描述
						String columnName = cloumnsSet.getString("COLUMN_NAME"); // 获取列名
						String javaType = CodeGeneratorUtils.getType(cloumnsSet.getInt("DATA_TYPE"));// 获取类型，并转成JavaType
						int COLUMN_SIZE = cloumnsSet.getInt("COLUMN_SIZE");// 获取
						String TABLE_SCHEM = cloumnsSet.getString("TABLE_SCHEM");// 获取
						String COLUMN_DEF = cloumnsSet.getString("COLUMN_DEF");// 获取
						int NULLABLE = cloumnsSet.getInt("NULLABLE");// 获取
						// showColumnInfo(cloumnsSet);
						String propertyName = CodeGeneratorUtils.replace_(CodeGeneratorUtils.replaceRow(columnName));// 处理列名，驼峰
						typeSet.add(javaType);// 需要导包的类型
						if (columnName.equals(key)) {
							keyType = CodeGeneratorUtils.simpleName(javaType);// 主键类型,单主键支持
						}
						// V1 初始化数据及对象
						FieldInfo fieldInfo = new FieldInfo();
						fieldInfo.setColumnName(columnName);
						fieldInfo.setFieldName(propertyName);
						fieldInfo.setFieldClass(CodeGeneratorUtils.simpleName(javaType));
						fieldInfo.setFieldComment(remarks);
						fieldList.add(fieldInfo);
					}
					// ************************************************************************
					if (fieldList != null && fieldList.size() > 0) {
						ClassInfo classInfo = new ClassInfo();
						classInfo.setTableName(tableName);
						classInfo.setClassName(classNameFirstUpper);
						classInfo.setClassComment(tableComment);
						classInfo.setFieldList(fieldList);
						list.add(classInfo);
					}
					// ************************************************************************
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Boolean skipTables(String str) {
		str = str.toLowerCase();
		for (String x : CodeGeneratorUtils.SKIPTABLE.split(",")) {
			if (str.contains(x.toLowerCase())) {
				return true;
			}
		}
		return true;
	}

	public static Boolean includeTabbles(String str) {
		str = str.toLowerCase();
		if (CodeGeneratorUtils.INCLUETABLES.equals("*")) {
			return false;
		}
		for (String x : CodeGeneratorUtils.INCLUETABLES.split(",")) {
			if (str.contains(x.toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public static void getFile(String path, List<Map<String, Object>> list) {
		File file = new File(path);
		File[] array = file.listFiles();
		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// only take file name
				// System.out.println("^^^^^" + array[i].getName());
				// take file path and name
				// System.out.println("*****" + array[i].getPath());
				map.put(array[i].getName(), array[i].getPath());
				list.add(map);
			} else if (array[i].isDirectory()) {
				getFile(array[i].getPath(), list);
			}
		}
	}

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

	public static String firstLower(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String replaceTabblePreStr(String str) {
		return str.replaceFirst("tab_", "").replaceFirst("tb_", "");
	}

	public static String replaceRow(String str) {
		str = str.toLowerCase().replaceFirst("tab_", "").replaceFirst("tb_", "").replaceFirst("t_", "");
		for (String x : CodeGeneratorUtils.ROWREMOVEPREFIXES.split(",")) {
			str = str.replaceFirst(x.toLowerCase(), "");
		}
		return str;
	}

	public static String simpleNameLowerFirst(String type) {
		// 去掉前缀
		type = simpleName(type);
		// 将第一个字母转成小写
		return CodeGeneratorUtils.firstLower(type);
	}

	public static String simpleName(String type) {
		return type.replace("java.lang.", "").replaceFirst("java.util.", "");
	}

	public static String upperCaseFirstWord(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
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

	/***
	 * 构建 Java文件，遍历文件夹下所有的模板，然后生成对应的文件（需要配置模板的package及path）
	 */
	public static void batchBuilderByDirectory(Map<String, Object> modelMap) {
		List<Map<String, Object>> srcFiles = new ArrayList<Map<String, Object>>();
		getFile(CodeGeneratorUtils.TEMPLATE_PATH, srcFiles);
		for (int i = 0; i < srcFiles.size(); i++) {
			HashMap<String, Object> m = (HashMap<String, Object>) srcFiles.get(i);
			Set<String> set = m.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((boolean) modelMap.get("Swagger") == true) {
					if (!key.contains(".json")) {
						continue;
					}
				}
				String templateFileName = key;
				String templateFileNameSuffix = key.substring(key.lastIndexOf("."), key.length());
				String templateFileNamePrefix = key.substring(0, key.lastIndexOf("."));
				String templateFilePathAndName = String.valueOf(m.get(key));
				String templateFilePath = templateFilePathAndName.replace("\\" + templateFileName, "");
				String templateFilePathMiddle = "";
				if (!templateFilePath.endsWith(CodeGeneratorUtils.TEMPLATE_NAME.replace("/", "\\"))) {
					templateFilePathMiddle = templateFilePath
							.substring(templateFilePath.lastIndexOf("\\"), templateFilePath.length()).replace("\\", "");
				}
				if (key.contains(".json")) {
					logger.info("templateFilePath=" + templateFilePath);
					continue;
				}
				try {
					String path = null;
					if (templateFileNameSuffix.equalsIgnoreCase(".java")) {
						// 创建文件夹
						path = CodeGeneratorUtils.PROJECT_PATH + "/" + CodeGeneratorUtils.PACKAGE_BASE.replace(".", "/")
								+ "/" + templateFileNamePrefix.toLowerCase();
					}
					if (templateFileNameSuffix.equalsIgnoreCase(".ftl")) {
						path = CodeGeneratorUtils.PROJECT_PATH + "/" + CodeGeneratorUtils.PACKAGE_BASE.replace(".", "/")
								+ "/" + templateFilePathMiddle + "/";
					}
					String fileNameNew = templateFileNamePrefix
							.replace("${className}", String.valueOf(modelMap.get("Table")))
							.replace("${classNameLower}", String.valueOf(modelMap.get("Table")).toLowerCase());
					// 创建文件
//					GeneratorUtils.writer(template, modelMap, path + "/" + fileNameNew);
					CodeGeneratorUtils.processFile(templateFileName, modelMap, path + "/" + fileNameNew);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void showTableInfo(ResultSet tableResultSet) throws SQLException {
		System.out.println(tableResultSet.getString("TABLE_CAT"));
		System.out.println(tableResultSet.getString("TABLE_SCHEM"));
		System.out.println(tableResultSet.getString("TABLE_NAME"));
		System.out.println(tableResultSet.getString("TABLE_TYPE"));
		System.out.println(tableResultSet.getString("REMARKS"));
	}

	public static void showColumnInfo(ResultSet cloumnsSet) throws SQLException {
		System.out.println("TABLE_CAT is :" + cloumnsSet.getString("TABLE_CAT"));
		System.out.println("TABLE_SCHEM is :" + cloumnsSet.getString("TABLE_SCHEM"));
		System.out.println("TABLE_NAME is :" + cloumnsSet.getString("TABLE_NAME"));
		System.out.println("COLUMN_NAME is :" + cloumnsSet.getString("COLUMN_NAME"));
		System.out.println("DATA_TYPE is :" + cloumnsSet.getInt("DATA_TYPE"));
		System.out.println("TYPE_NAME is :" + cloumnsSet.getString("TYPE_NAME"));
		System.out.println("COLUMN_SIZE is :" + cloumnsSet.getInt("COLUMN_SIZE"));
		System.out.println("BUFFER_LENGTH is :" + cloumnsSet.getInt("BUFFER_LENGTH"));
		System.out.println("DECIMAL_DIGITS is :" + cloumnsSet.getInt("DECIMAL_DIGITS"));
		System.out.println("NUM_PREC_RADIX is :" + cloumnsSet.getInt("NUM_PREC_RADIX"));
		System.out.println("NULLABLE is :" + cloumnsSet.getInt("NULLABLE"));
		System.out.println("REMARKS is :" + cloumnsSet.getString("REMARKS"));
		System.out.println("COLUMN_DEF is :" + cloumnsSet.getString("COLUMN_DEF"));
		System.out.println("SQL_DATA_TYPE is :" + cloumnsSet.getInt("SQL_DATA_TYPE"));
		System.out.println("SQL_DATETIME_SUB is :" + cloumnsSet.getInt("SQL_DATETIME_SUB"));
		System.out.println("CHAR_OCTET_LENGTH is :" + cloumnsSet.getInt("CHAR_OCTET_LENGTH"));
		System.out.println("ORDINAL_POSITION is :" + cloumnsSet.getInt("ORDINAL_POSITION"));
		System.out.println("IS_NULLABLE is :" + cloumnsSet.getString("IS_NULLABLE"));
	}

}
