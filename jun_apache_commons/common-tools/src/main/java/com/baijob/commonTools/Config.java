package com.baijob.commonTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取配置文件的类
 * @author Luxiaolei
 */
public final class Config {
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	
	/*--------------------------私有属性 start-------------------------------*/
	private Properties props;
	/*--------------------------私有属性 end-------------------------------*/
	
	/**
	 * 构造，使用相对于Class文件根目录的相对路径
	 * @param pathBaseClassLoader 相对路径（相对于当前项目的classes路径）
	 */
	public Config(String pathBaseClassLoader){
		URL url = Config.class.getClassLoader().getResource(pathBaseClassLoader);
		logger.debug("加载配置文件 => " + url.getPath());
		this.init(url);
	}
	
	/**
	 * 构造
	 * @param configFile 配置文件对象
	 */
	public Config(File configFile){
		if(configFile == null){
			logger.error("请指定配置文件！");
			return;
		}
		URL url = null;
		try {
			url = configFile.toURI().toURL();
			logger.debug("加载配置文件 => " + url.getPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.init(url);
	}
	
	/**
	 * 构造，相对于classes读取文件
	 * @param path 相对路径
	 * @param clazz 基准类
	 */
	public Config(String path, Class<?> clazz){
		URL url = clazz.getResource(path);
		this.init(url);
	}
	
	/*--------------------------公有方法 start-------------------------------*/
	/**
	 * 初始化配置文件
	 * @param configUrl 配置文件URL
	 */
	public  void init(URL configUrl){
		if(configUrl == null){
			logger.error("指定配置文件路径为空，请检查！");
			return;
		}
		InputStream in = null;
		props = new Properties();
		try {
			in = configUrl.openStream();
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			FileUtil.close(in);
		}
	}
	
	/**
	 * 获取字符型型属性值
	 * @param key 属性名
	 * @return 属性值
	 */
	public String getString(String key){
		return props.getProperty(key);
	}
	
	/**
	 * 获取数字型型属性值
	 * @param key 属性名
	 * @return 属性值
	 */
	public int getInt(String key){
		return Integer.parseInt(props.getProperty(key));
	}
	
	/**
	 * 获取波尔型属性值
	 * @param key 属性名
	 * @return 属性值
	 */
	public boolean getBool(String key){
		return Boolean.parseBoolean(props.getProperty(key));
	}
	
	/**
	 * 获取long类型属性值
	 * @param key 属性名
	 * @return 属性值
	 */
	public long getLong(String key){
		return Long.parseLong(props.getProperty(key));
	}
	
	/**
	 * 设置值，无给定键创建之。设置后未持久化
	 * @param key 属性键
	 * @param value 属性值
	 */
	public void setProperty(String key, Object value){
		props.setProperty(key, value.toString());
	}
	
	/**
	 * 持久化当前设置，会覆盖掉之前的设置
	 * @param absolutePath 设置文件的绝对路径
	 */
	public void store(String absolutePath){
		try {
			FileUtil.touch(absolutePath);
			props.store(FileUtil.getOutputStream(absolutePath), null);
		} catch (FileNotFoundException e) {
			//不会出现这个异常
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 存储当前设置，会覆盖掉以前的设置
	 * @param path 相对路径
	 * @param clazz 相对的类
	 */
	public void store(String path, Class<?> clazz){
		this.store(FileUtil.getAbsolutePath(path, clazz));
	}
	
	public Set<Entry<Object, Object>> entrySet(){
		return props.entrySet();
	}
	/*--------------------------公有方法 end-------------------------------*/
}
