package com.springboot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	private Properties properties = null;
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

	public PropertiesUtil(String fileName) {
		this(fileName, "UTF-8");
	}

	public PropertiesUtil(File file) {
		this(file, "UTF-8");
	}

	public PropertiesUtil(String fileName, String charset) {
		InputStream inputStream = null;
		try {
			inputStream = getClassLoader().getResourceAsStream(fileName); // properties.load(Prop.class.getResourceAsStream(fileName));
			if (inputStream == null) {
				throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
			}
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, charset));
		} catch (IOException e) {
			throw new RuntimeException("Error loading properties file.", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

	public PropertiesUtil(File file, String charset) {
		if (file == null) {
			throw new IllegalArgumentException("File can not be null.");
		}
		if (file.isFile() == false) {
			throw new IllegalArgumentException("File not found : " + file.getName());
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, charset));
		} catch (IOException e) {
			throw new RuntimeException("Error loading properties file.", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

	private ClassLoader getClassLoader() {
		ClassLoader ret = Thread.currentThread().getContextClassLoader();
		return ret != null ? ret : getClass().getClassLoader();
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public Integer getInt(String key) {
		return getInt(key, null);
	}

	public Integer getInt(String key, Integer defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			return Integer.parseInt(value.trim());
		}
		return defaultValue;
	}

	public Long getLong(String key) {
		return getLong(key, null);
	}

	public Long getLong(String key, Long defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			return Long.parseLong(value.trim());
		}
		return defaultValue;
	}

	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			value = value.toLowerCase().trim();
			if ("true".equals(value)) {
				return true;
			} else if ("false".equals(value)) {
				return false;
			}
			throw new RuntimeException("The value can not parse to Boolean : " + value);
		}
		return defaultValue;
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	public Properties getProperties() {
		return properties;
	}

}
