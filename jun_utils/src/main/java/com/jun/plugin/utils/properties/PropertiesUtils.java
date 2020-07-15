package com.jun.plugin.utils.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;


public class PropertiesUtils {
	private static Properties properties = new Properties();
	private static Map<String, String> propertyMap = new HashMap<String, String>();
	public static Map<String, String> loadSqlMap = new HashMap<String, String>();
	private static String fileName = "config.properties";
	private static InputStream in = null;
	static {
		try {
			in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String getProperties(String key) {
		return (String) properties.get(key);
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getPropertyRelaod(String key) throws FileNotFoundException, IOException {
		String value = PropertiesUtils.propertyMap.get(key);
		// Properties properties = new Properties();
		// InputStream inputFile = null;
		if (value == null) {
			// 实例化inputFile,如config.properties文件的位置
			// inputFile = new FileInputStream(PropertiesUtils.fileName);
			properties.load(in);
			value = properties.getProperty(key);
			PropertiesUtils.propertyMap.put(key, value);
		}
		return value;
	}

	public static boolean addProperty2(String key, String value) throws FileNotFoundException, IOException {
		// String value = Property.propertyMap.get(key);
		// Properties property = new Properties();
		// FileInputStream inputFile = null;
		// FileOutputStream ouputFile = null;
		if (value != null) {
			// 实例化inputFile,如config.properties文件的位置
			// inputFile = new FileInputStream(PropertiesUtils.fileName);
			// 装载配置文件
			properties.load(in);
			if (properties.containsKey(key)) {
				properties.setProperty(key, value);
			} else {
				properties.put(key, value);
			}
			// property.save(out, "");
			// value = property.getProperty(key);
			// Property.propertyMap.put(key, value);
		}
		return true;
	}

	public static Map<String, String> getProperties(List<String> propertyList) throws FileNotFoundException, IOException {
		// 定义Map用于存放结果
		// Map<String, String> propertyMap = new HashMap<String, String>();
		// Properties property = new Properties();
		// FileInputStream inputFile = null;
		try {
			// inputFile = new FileInputStream(PropertiesUtils.fileName);
			properties.load(in);
			for (String name : propertyList) {
				String data = properties.getProperty(name);
				propertyMap.put(name, data);
			}
		} finally {
			// if (inputFile != null) {
			// inputFile.close();
			// }
		}
		return propertyMap;
	}
	
//	public InputStream getPropsIS() {
//		InputStream ins = this.getClass().getResourceAsStream("/config.properties");
//		return ins;
//	}
	public static String getPropsFilePath() {
		String filePath = PropertiesUtils.class.getClass().getResource("/").getPath().toString();
		filePath = filePath.substring(0, filePath.indexOf("classes") - 1) + "/config.properties";
		return filePath;
	}
	public String readSingleProps(String attr) {
		String retValue = "";
		Properties props = new Properties();
		try {
			/*
			 * if (!FileUtil.isFileExist(getPropsFilePath())) { return ""; } FileInputStream fi = new FileInputStream(getPropsFilePath());
			 */
//			InputStream fi = getPropsIS();
			props.load(in);
			in.close();
			retValue = props.getProperty(attr);
		} catch (Exception e) {
			return "";
		}
		return retValue;
	}
	@SuppressWarnings("rawtypes")
	public static HashMap getAllProps() {
		HashMap h = new HashMap();
		Properties props = new Properties();
		try {
			/*
			 * if (!FileUtil.isFileExist(getPropsFilePath())) return new HashMap(); FileInputStream fi = new FileInputStream(getPropsFilePath());
			 */
//			InputStream fi = getPropsIS();
			props.load(in);
			in.close();
			Enumeration er = props.propertyNames();
			while (er.hasMoreElements()) {
				String paramName = (String) er.nextElement();
				h.put(paramName, props.getProperty(paramName));
			}
		} catch (Exception e) {
			return new HashMap();
		}
		return h;
	}

	/**
	 * @Title: loadSqlFile
	 * @Description: 加载sql.properties文件,并获取其中的内容(key-value)
	 * @param filePath
	 *            : 文件路径
	 * @author
	 * @date 2011-12-28
	 */
	public static void loadSqlFile(String filePath) {
		if (null == filePath || "".equals(filePath.trim())) {
			System.out.println("The file path is null,return");
			return;
		}
		filePath = filePath.trim();
		// 获取资源文件
		InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath);
		// 属性列表
		Properties prop = new Properties();
		try {
			// 从输入流中读取属性列表
			prop.load(is);
		} catch (IOException e) {
			System.out.println("load file faile." + e);
			return;
		} catch (Exception e) {
			System.out.println("load file faile." + e);
			return;
		}
		// 返回Properties中包含的key-value的Set视图
		Set<Entry<Object, Object>> set = prop.entrySet();
		// 返回在此Set中的元素上进行迭代的迭代器
		Iterator<Map.Entry<Object, Object>> it = set.iterator();
		String key = null, value = null;
		// 循环取出key-value
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			key = String.valueOf(entry.getKey());
			value = String.valueOf(entry.getValue());
			key = key == null ? key : key.trim().toUpperCase();
			value = value == null ? value : value.trim().toUpperCase();
			// 将key-value放入map中
			loadSqlMap.put(key, value);
			System.out.println(key + "=" + value);
		}
	}

	// ******************************************bundle***************************************************
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	public static final String getPropertiesByName(String name) {
		return bundle.getString(name);
	}

	public void Test() {
		System.err.println(PropertiesUtils.getPropertiesByName("key"));
	}

	// ******************************************bundle****************************************************

	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Test
	public void TEST() throws FileNotFoundException, IOException {
		System.err.println(PropertiesUtils.getProperties("key"));
		System.err.println(PropertiesUtils.getProperty("key"));
		System.err.println(PropertiesUtils.getPropertyRelaod("key"));
		List propertyList = new ArrayList();
		propertyList.add("key");
		propertyList.add("wu");
		System.err.println(PropertiesUtils.getProperties(propertyList).get(("wu")));
		System.err.println(PropertiesUtils.addProperty2("aaa", "bbb"));

		PropertiesUtils.loadSqlFile("config.properties");
		// LoadPopertiesFile.loadSqlFile("config.properties");
		System.out.println(loadSqlMap);
	}

//	@Test
	public void Test2() throws IOException {
		String pa = PropertiesUtils.class.getClassLoader().getResource("config.properties").getPath();
		pa = URLDecoder.decode(pa, "UTF-8");
		FileInputStream input = new FileInputStream(pa);
		Properties p = new Properties();
		p.load(input);
		System.out.println(p.getProperty("name"));
		System.out.println(p.getProperty("username"));
	}

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
//	@Test
	public void Test3() throws FileNotFoundException, IOException {
		// String path="D:/workspace/workspace_myeclipse_mine/D_MINE/config/config.properties";
		String path = "config/config.properties";
//		PropertiesUtils per = new PropertiesUtils(path);
		System.out.println(properties.getProperty("weblogic_ip"));
		List list = new ArrayList();
		list.add("weblogic_ip");
		list.add("weblogic_port");
		System.out.println(PropertiesUtils.getProperties(list));
		PropertiesUtils.addProperty2("en_name11", "jack");
		PropertiesUtils.addProperty2("en_name12", "jack11");
		System.out.println(PropertiesUtils.getProperty("en_name"));
	}
//	 prop.clear(); // 清空2         
//	 prop.containsKey("key"); // 是否包含key3         
//	 prop.containsValue("value"); // 是否包含value4         
//	 prop.entrySet(); // prop的Map.Entry集合5         
//	 prop.getProperty("key"); // 通过key获取value6         
//	 prop.put("key", "value"); // 添加属性7         
//	 prop.list(new PrintStream(new File(""))); // 将prop保存到文件8         
//	 prop.store(new FileOutputStream(new File("")), "注释"); // 和上面类似
}
