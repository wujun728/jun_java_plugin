package com.jun.plugin.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 读取 配置文件。
 */
public class PropertiesUtil {

//	@Test
	public void saveProps() {
		PropertiesUtil.getInstance().setProperty("password", "5314321ww");
		System.err.println(PropertiesUtil.getInstance().getConfiguration("password"));
	}

//	@Test
	public void outPutProps() {
		Properties p = new Properties();
		p.setProperty("C", "China");
		p.setProperty("A", "America");
		p.setProperty("J", "Japan");
		p.setProperty("K", "Korea");
		p.setProperty("S", "Spain");
		Enumeration e = p.propertyNames();
		System.out.print("The all keys in p:");
		while (e.hasMoreElements())
			System.out.println(e.nextElement().toString() + "\t");
		System.out.println();
		System.out.println("The property of key 'K' is " + p.getProperty("K"));
		System.out.println("The property of key 'J' is " + p.getProperty("J"));
		System.out.println("The property of key 'Q' is " + p.getProperty("Q"));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final String CONF_CLASSPATH = "/config.properties";

	private static PropertiesUtil instance = new PropertiesUtil();

	private volatile Properties configuration = new Properties();

	FileOutputStream os = null;

	public PropertiesUtil() {

		InputStream is = this.getClass().getResourceAsStream(CONF_CLASSPATH);

		if (is != null) {
			try {
				this.configuration.clear();
				this.configuration.load(is);
			} catch (IOException e) {
			} finally {
				try {
					is.close();
				} catch (Throwable t) {
				}
			}
		} else {
		}
	}

	/**
	 * 获得Configuration实例。
	 * 
	 * @return Configuration实例
	 */
	public static PropertiesUtil getInstance() {

		return instance;
	}

	/**
	 * 获得配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public String getConfiguration(String key) {

		return this.configuration.getProperty(key);
	}

	public String getProperty(String key) {
		return getConfiguration(key);
	}

	public void setProperty(String key, String value) {
		setConfiguration(key, value);
		store("setProperty");
	}

	/**
	 * 取得String类型的值，如果配置项不存在，则返回defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getStringValue(String key, String defaultValue) {
		String value = this.getConfiguration(key);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public void setConfiguration(String key, String value) {
		this.configuration.setProperty(key, value);

	}

	public void store(String comments) {
		if (configuration != null) {
			try {
				String path = getClass().getResource(CONF_CLASSPATH).getPath();
				os = new FileOutputStream(path);
				configuration.store(os, comments);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (Exception ee) {
					}
				}
			}
		}
	}

	/**
	 * 获得整型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public Integer getIntegerConfiguration(String key) {

		Integer result = null;

		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			try {
				result = new Integer(Integer.parseInt(value));
			} catch (NumberFormatException nfe) {
			}
		}

		return result;
	}

	/**
	 * 获得短整型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public Short getShortConfiguration(String key) {
		Short result = null;
		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			try {
				result = new Short(Short.parseShort(value));
			} catch (NumberFormatException nfe) {
			}
		}

		return result;
	}

	/**
	 * 获得长整型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public Long getLongConfiguration(String key) {

		Long result = null;

		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			try {
				result = new Long(Long.parseLong(value));
			} catch (NumberFormatException nfe) {
			}
		}

		return result;
	}

	/**
	 * 获得单精度浮点型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public Float getFloatConfiguration(String key) {

		Float result = null;

		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			try {
				result = new Float(Float.parseFloat(value));
			} catch (NumberFormatException nfe) {
			}
		}

		return result;
	}

	/**
	 * 获得双精度浮点型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public Double getDoubleConfiguration(String key) {

		Double result = null;

		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			try {
				result = new Double(Double.parseDouble(value));
			} catch (NumberFormatException nfe) {
			}
		}

		return result;
	}

	/**
	 * 获得布尔型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @return 配置项
	 */
	public Boolean getBooleanConfiguration(String key) {

		Boolean result = null;

		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			if ("true".equalsIgnoreCase(value)) {
				result = new Boolean(true);
			} else if ("false".equalsIgnoreCase(value)) {
				result = new Boolean(false);
			}
		}

		return result;
	}

	/**
	 * 获得日期型配置项。
	 * 
	 * @param key
	 *            配置关键字
	 * @param pattern
	 *            日期字符串格式
	 * @return 配置项
	 */
	public Date getDateConfiguration(String key, String pattern) {

		Date result = null;

		String value = this.configuration.getProperty(key);
		if ((value != null) && !"".equals(value)) {
			try {
				if ((pattern != null) && !"".equals(pattern)) {
					result = (new SimpleDateFormat(pattern)).parse(value);
				} else {
					result = (new SimpleDateFormat()).parse(value);
				}
			} catch (ParseException pe) {
			}
		}

		return result;
	}

	/**
	 * 获取.properties属性文件所有属性.
	 * 
	 * @param path
	 *            属性文件路径
	 * @return <code>Properties</code> 属性
	 * @throws IOException
	 *             IO Exception
	 */
	public static Properties getProperties(String path) throws IOException {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(new File(path));
			properties = getProperties(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return properties;
	}

	/**
	 * 获取所有属性.
	 * 
	 * @param in
	 *            InputStream
	 * @return <code>Properties</code> 属性
	 * @throws IOException
	 *             IO Exception
	 */
	public static Properties getProperties(InputStream in) throws IOException {
		Properties properties = new Properties();
		properties.load(in);
		return properties;
	}

	/**
	 * 获取.properties属性文件所有属性的Map集合.
	 * 
	 * @param path
	 *            属性文件路径
	 * @return <code>Map<String, String></code> 属性的Map集合
	 * @throws IOException
	 *             IO Exception
	 */
	public static Map<String, String> getPropertiesMap(String path) throws IOException {
		Properties properties = getProperties(path);
		return getPropertiesMap(properties);
	}

	/**
	 * 获取所有属性.
	 * 
	 * @param in
	 *            InputStream
	 * @return <code>Map<String, String></code> 属性的Map集合
	 * @throws IOException
	 *             IO Exception
	 */
	public static Map<String, String> getPropertiesMap(InputStream in) throws IOException {
		Properties properties = getProperties(in);
		return getPropertiesMap(properties);
	}

	/**
	 * 获取properties属性的Map集合.
	 * 
	 * @param properties
	 *            属性
	 * @return <code>Map<String, String></code> 属性的Map集合
	 */
	public static Map<String, String> getPropertiesMap(Properties properties) {
		Set<String> keySets = properties.stringPropertyNames();
		Object[] keys = keySets.toArray();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			String key = (String) keys[i];
			map.put(key, properties.getProperty(key));
		}
		return map;
	}

	/**
	 * 获取.properties属性单个属性的方法，获取两个或两个以上属性值使用getPropertiesMap(path)方法.
	 * 
	 * @param path
	 *            属性文件路径
	 * @param property
	 *            属性key
	 * @return <code>String</code> 属性值
	 * @throws IOException
	 *             IO Exception
	 * @see #getPropertiesMap(String)
	 */
	public static String getProperty(String path, String property) throws IOException {
		return getPropertiesMap(path).get(property);
	}

}