package com.jun.plugin.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jun.mis.entity.Resource;
/**
 * 类功能说明 TODO: 项目参数工具类
 * 类修改者	修改日期
 * 修改说明
 * <p>Title: BaseService.java</p>
 * <p>Description:福产流通科技</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company:福产流通科技</p>
 * @author shouyin 756514656@qq.com
 * @date 2013-4-19 下午03:18:05
 * @version V1.0
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");
//	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	public static final String getPropertiesByName(String name) {
		return bundle.getString(name);
	}

	public void Test() {
		System.err.println(ConfigUtil.getPropertiesByName("key"));
	}

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 获得上传表单域的名称
	 * 
	 * @return
	 */
	public static final String getUploadFieldName() {
		return bundle.getString("uploadFieldName");
	}

	/**
	 * 获得上传文件的最大大小限制
	 * 
	 * @return
	 */
	public static final long getUploadFileMaxSize() {
		return Long.valueOf(bundle.getString("uploadFileMaxSize"));
	}

	/**
	 * 获得允许上传文件的扩展名
	 * 
	 * @return
	 */
	public static final String getUploadFileExts() {
		return bundle.getString("uploadFileExts");
	}

	/**
	 * 获得上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getUploadDirectory() {
		return bundle.getString("uploadDirectory");
	}
//**************************************************************************************************
//**************************************************************************************************
	/**
	 * 生成Tree结构的HTML代码块
	 * @param topResourceList 顶级权限的一级子权限集合
	 * @param rootName 页面上根节点UL的id名称
	 * @param isRoot 是否为根节点
	 * @param addHref  是否为节点添加超链接
	 * @param targetName 链接到的frame的name名称
	 */
	public static String getTreeHTML(Collection<Resource> topResourceList,String rootName,boolean isRoot,boolean addHref,String targetName){
		if(topResourceList.size() == 0){
			return "";
		}
		StringBuffer treeHTML = new StringBuffer();
		treeHTML.append(isRoot?"<ul id=\"" + rootName + "\" class=\"filetree\">":"<ul>");
		for(Resource resource:topResourceList){
			treeHTML.append("<li class=\"closed\">");
			treeHTML.append("    <input type=\"checkbox\" id=\"ch_" + resource.getId() + "\" name=\"roleVO.resourceIds\" value=\"" + resource.getId() + "\"/>");
			//treeHTML.append("<label for=\"ch_" + resource.getId()+ "\"><span class=\"folder\">" + resource.getName()+"</span></label>");
			//treeHTML.append("<span class=\"folder\">" + resource.getName()+"</span>");
			treeHTML.append("<label for=\"ch_" + resource.getId()+ "\">");
			if(resource.isLeaf()){
				treeHTML.append("<span class=\"file\">");
			} else{
				treeHTML.append("<span class=\"folder\">");
			}
			/*if(addHref && !GerneralUtil.isNULLOrPropertyEmpty(resource.getUrl())){
				treeHTML.append("<a href=\"").append(resource.getUrl()).append("\"");
				treeHTML.append(" target=\"").append(targetName).append("\">");
			}*/
			treeHTML.append(resource.getName());
			/*if(addHref && !GerneralUtil.isNULLOrPropertyEmpty(resource.getUrl())){
				treeHTML.append("</a>");
			}*/
			treeHTML.append("</span></label>");
			treeHTML.append(getTreeHTML(resource.getChildren(),rootName,false,addHref,targetName));
			treeHTML.append("</li>");
		}
		treeHTML.append("</ul>");
		return treeHTML.toString();
	}
	
	/**
	 * 返回权限树形字符串
	 * @param topresourceCollection  顶级权限集合
	 * @param prefix                   输出权限名称的前缀
	 * @parem list                     权限输出集合
	 * @return
	 */
	private static void getResourceTreesString(Collection<Resource> topResourceCollection,String prefix,List<Resource> list){
		for (Resource resource : topResourceCollection) {
			Resource copy = new Resource();
			copy.setId(resource.getId());
			copy.setName(prefix + "┝" + resource.getName());
			list.add(copy);
			getResourceTreesString(resource.getChildren(),prefix + "　",list);
		}
	}
	
	/**
	 * 根据顶级权限获取所有权限(权限名称经过修改)
	 */
	public static List<Resource> getAllResourceList(List<Resource> topResourceList){
		List<Resource> list = new ArrayList<Resource>();
		getResourceTreesString(topResourceList,"",list);
		return list;
	}
	
	/**
	 * 给定集合中移除指定权限及其子权限
	 */
	public static void removeSomeResourcesAndChildren(List<Resource> resourceList,Resource resource){
		resourceList.remove(resource);
		for (Resource rs : resource.getChildren()) {
			removeSomeResourcesAndChildren(resourceList,rs);
		}
	}
	
	/**
	 * 给定集合中移除指定权限的子权限
	 * @param isfirst true表示不删除当前权限 false表示删除当前权限及其子权限
	 */
	public static void removeSomeResourcesAndChildren(List<Resource> resourceList,Resource resource,boolean isfirst){
		if(!isfirst){
			resourceList.remove(resource);
		}
		for (Resource rs : resource.getChildren()) {
			isfirst = false;
			removeSomeResourcesAndChildren(resourceList,rs);
		}
	}
//**************************************************************************************************
//**************************************************************************************************
	
	/**
	 * 获取数据库类型
	 * 
	 * @return
	 */
	public static final String getJdbcUrl() {
		return bundle.getString("jdbc_url").toLowerCase();
	}

//**************************************************************************************************
//**************************************************************************************************
	

	private static Properties properties = new Properties();
	private static Map<String, String> propertyMap = new HashMap<String, String>();
	public static Map<String, String> loadSqlMap = new HashMap<String, String>();
	private static String fileName = "config.properties";
	private static InputStream in = null;
	static {
		try {
			in = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);
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
		String value = ConfigUtil.propertyMap.get(key);
		// Properties properties = new Properties();
		// InputStream inputFile = null;
		if (value == null) {
			// 实例化inputFile,如config.properties文件的位置
			// inputFile = new FileInputStream(ConfigUtils.fileName);
			properties.load(in);
			value = properties.getProperty(key);
			ConfigUtil.propertyMap.put(key, value);
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
			// inputFile = new FileInputStream(ConfigUtils.fileName);
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
			// inputFile = new FileInputStream(ConfigUtils.fileName);
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
		String filePath = ConfigUtil.class.getClass().getResource("/").getPath().toString();
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
		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(filePath);
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


	// ******************************************bundle****************************************************

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void TEST() throws FileNotFoundException, IOException {
		System.err.println(ConfigUtil.getProperties("key"));
		System.err.println(ConfigUtil.getProperty("key"));
		System.err.println(ConfigUtil.getPropertyRelaod("key"));
		List propertyList = new ArrayList();
		propertyList.add("key");
		propertyList.add("wu");
		System.err.println(ConfigUtil.getProperties(propertyList).get(("wu")));
		System.err.println(ConfigUtil.addProperty2("aaa", "bbb"));

		ConfigUtil.loadSqlFile("config.properties");
		// LoadPopertiesFile.loadSqlFile("config.properties");
		System.out.println(loadSqlMap);
	}

//	@Test
	public void Test2() throws IOException {
		String pa = ConfigUtil.class.getClassLoader().getResource("config.properties").getPath();
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
//		ConfigUtils per = new ConfigUtils(path);
		System.out.println(properties.getProperty("weblogic_ip"));
		List list = new ArrayList();
		list.add("weblogic_ip");
		list.add("weblogic_port");
		System.out.println(ConfigUtil.getProperties(list));
		ConfigUtil.addProperty2("en_name11", "jack");
		ConfigUtil.addProperty2("en_name12", "jack11");
		System.out.println(ConfigUtil.getProperty("en_name"));
	}
//	 prop.clear(); // 清空2         
//	 prop.containsKey("key"); // 是否包含key3         
//	 prop.containsValue("value"); // 是否包含value4         
//	 prop.entrySet(); // prop的Map.Entry集合5         
//	 prop.getProperty("key"); // 通过key获取value6         
//	 prop.put("key", "value"); // 添加属性7         
//	 prop.list(new PrintStream(new File(""))); // 将prop保存到文件8         
//	 prop.store(new FileOutputStream(new File("")), "注释"); // 和上面类似
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/

	private static final boolean isWin = System.getProperty("os.name").toUpperCase().substring(0,3).equals("WIN");
	private static Logger logger = Logger.getLogger(ConfigUtil.class);
	private static String basePath;

	static {		
		basePath = getPath(ConfigUtil.class,"");
		basePath = new File(basePath).getParent();
		logger.debug("basepath:" + basePath);
	}
	
	public static String getPath(Class<?> classz,String filename) {
		URL url = classz.getClassLoader().getResource(filename);
		String configPath = null;
		if (url != null) {
			configPath = url.getPath().replaceAll("%20", " ");
			logger.debug(configPath);
			if (isWin) {
				configPath = configPath.toLowerCase();
			}
		}
		return configPath;
	}
	
	public static String getPath(String filename) {
		filename = filename.replaceAll("%20", " ");
		if (isWin) {
			filename = filename.toLowerCase();
		}

		String path = basePath + filename;
		return path;
	}
	
	public static Properties getProperties(Class<?> classz,String filename) {
		Properties p = new Properties();
		String configPath = getPath(classz,filename);
		try {        	
		            
            InputStream input = new FileInputStream(configPath);
            if (input != null) {
            	p.load(input);
            }
		} catch (Exception e) {
			logger.error("读取配置文件失败",e);
		}
		return p;
	}
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	private static final String WINDOWS = "WINDOWS";

	/**
	 * 判断当前操作系统的类型
	 * 
	 * @return false means window system ,true means unix like system
	 */
	public static boolean isUnixLikeSystem() {
		String name = System.getProperty("os.name");
		if (name != null) {
			if (name.toUpperCase().indexOf(WINDOWS) == -1) { // it means it's unix like system
				return true;
			}
		}
		return false;
	}
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/

	private static ResourceBundle resourceBundle;

	/**
	 * 实例化ResourceBundle
	 * @param resourceFileName 资源文件名，含包名
	 * @param local 本地化 zh_CN/en_US
	 * @return
	 */
    public static ResourceBundle getInstance(String resourceFileName,String local){
    	resourceBundle = ResourceBundle.getBundle(resourceFileName,new Locale(local));
    	return resourceBundle;
    }
    
    /**
	 * 实例化ResourceBundle
	 * @param resourceFileName 资源文件名，含包名
	 * @param local 本地化 Locale.CHINA / Locale.US
	 * @return ResourceBundle实例
	 */
    public static ResourceBundle getInstance(String resourceFileName,Locale local){
    	resourceBundle = ResourceBundle.getBundle(resourceFileName,local);
    	return resourceBundle;
    }
    /**
     * 根据key获取资源文件里对应的值
     * @param key
     * @return String
     */
    public static String getString(String key){
    	return resourceBundle.getString(key);
    }
    public static void main(String[] args) {
    	ConfigUtil.getInstance("com.yida.common.report.message", Locale.CHINA);
    	String value = ConfigUtil.getString("myReport.pageNumber");
    	System.out.println(value);
	}
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/

    
    //根据key读取value
    public static String readValue(String filePath,String key) {
     Properties props = new Properties();
           try {
            InputStream in = new BufferedInputStream (new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty (key);
               System.out.println(key+value);
               return value;
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }
    
    //读取properties的全部信息
       public static void readProperties(String filePath) {
        Properties props = new Properties();
           try {
            InputStream in = new BufferedInputStream (new FileInputStream(filePath));
            props.load(in);
               Enumeration en = props.propertyNames();
                while (en.hasMoreElements()) {
                 String key = (String) en.nextElement();
                       String Property = props.getProperty (key);
                       System.out.println(key+Property);
                   }
           } catch (Exception e) {
            e.printStackTrace();
           }
       }

       //写入properties信息
       public static void writeProperties(String filePath,String parameterName,String parameterValue) {
        Properties prop = new Properties();
        try {
         InputStream fis = new FileInputStream(filePath);
               //从输入流中读取属性列表（键和元素对）
               prop.load(fis);
               //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
               //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
               OutputStream fos = new FileOutputStream(filePath);
               prop.setProperty(parameterName, parameterValue);
               //以适合使用 load 方法加载到 Properties 表中的格式，
               //将此 Properties 表中的属性列表（键和元素对）写入输出流
               prop.store(fos, "Update '" + parameterName + "' value");
           } catch (IOException e) {
            System.err.println("Visit "+filePath+" for updating "+parameterName+" value error");
           }
       }

       public static void main4443(String[] args) {
        readValue("ProjectConfig.properties","url");
           writeProperties("ProjectConfig.properties","age","21");
//           readProperties("info.properties" );
           System.out.println("OK");
       }
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
}
