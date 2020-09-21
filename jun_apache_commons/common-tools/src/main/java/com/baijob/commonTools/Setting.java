package com.baijob.commonTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.Exceptions.SettingException;
import com.baijob.commonTools.net.URLUtil;

/**
 * 设置工具类。 用于支持设置文件 1、支持变量，默认变量命名为
 * 
 * @author Luxiaolei
 * 
 */
public class Setting extends HashMap<String, String> {
	private static final long serialVersionUID = -477527787843971824L;
	private static Logger logger = LoggerFactory.getLogger(Setting.class);

	/** 默认字符集 */
	public final static String DEFAULT_CHARSET = "utf8";

	/** 注释符号（当有此符号在行首，表示此行为注释） */
	private final static String COMMENT_FLAG_PRE = "#";
	/** 赋值分隔符（用于分隔键值对） */
	private final static String ASSIGN_FLAG = "=";
	/** 分组行识别的环绕标记 */
	private final static char[] GROUP_SURROUND = { '[', ']' };
	/** 变量名称的正则 */
	private String reg_var = "\\$\\{(.*?)\\}";

	/** 本设置对象的字符集 */
	private Charset charset;
	/** 是否使用变量 */
	private boolean isUseVariable;
	/** 设定文件的URL */
	private URL settingUrl;
	/** 分组的缓存 */
	private String group_cache;

	/**
	 * 基本构造<br/>
	 * 需自定义初始化配置文件<br/>
	 * 
	 * @param charset 字符集
	 * @param isUseVariable 是否使用变量
	 */
	public Setting(Charset charset, boolean isUseVariable) {
		this.charset = charset;
		this.isUseVariable = isUseVariable;
	}

	/**
	 * 构造，使用相对于Class文件根目录的相对路径
	 * 
	 * @param pathBaseClassLoader 相对路径（相对于当前项目的classes路径）
	 * @param charset 字符集
	 * @param isUseVariable 是否使用变量
	 */
	public Setting(String pathBaseClassLoader, String charset, boolean isUseVariable) {
		URL url = URLUtil.getURL(pathBaseClassLoader);
		this.init(url, charset, isUseVariable);
	}

	/**
	 * 构造
	 * 
	 * @param configFile 配置文件对象
	 * @param charset 字符集
	 * @param isUseVariable 是否使用变量
	 */
	public Setting(File configFile, String charset, boolean isUseVariable) {
		if (configFile == null) {
			logger.error("请指定配置文件！");
			return;
		}
		URL url = URLUtil.getURL(configFile);
		this.init(url, charset, isUseVariable);
	}

	/**
	 * 构造，相对于classes读取文件
	 * 
	 * @param path 相对路径
	 * @param clazz 基准类
	 * @param charset 字符集
	 * @param isUseVariable 是否使用变量
	 */
	public Setting(String path, Class<?> clazz, String charset, boolean isUseVariable) {
		URL url = URLUtil.getURL(path, clazz);
		this.init(url, charset, isUseVariable);
	}

	/**
	 * 构造
	 * 
	 * @param url 设定文件的URL
	 * @param charset 字符集
	 * @param isUseVariable 是否使用变量
	 */
	public Setting(URL url, String charset, boolean isUseVariable) {
		this.init(url, charset, isUseVariable);
	}

	/*--------------------------公有方法 start-------------------------------*/
	/**
	 * 初始化设定文件
	 * 
	 * @param settingUrl 设定文件的URL
	 * @param charset 字符集
	 * @param isUseVariable 是否使用变量
	 * @return 成功初始化与否
	 */
	public boolean init(URL settingUrl, String charset, boolean isUseVariable) {
		if (settingUrl == null || LangUtil.isEmpty(charset)) {
			logger.error("给定参数无效！");
			return false;
		}
		this.charset = Charset.forName(charset);
		this.isUseVariable = isUseVariable;
		this.settingUrl = settingUrl;

		return this.load(settingUrl);
	}

	/**
	 * 加载设置文件
	 * 
	 * @param settingUrl 配置文件URL
	 * @return 加载是否成功
	 */
	public boolean load(URL settingUrl) {
		if (settingUrl == null) {
			logger.error("Define setting url is null, please chech it !");
			return false;
		}
		logger.debug("Load setting file=>" + settingUrl.getPath());
		InputStream settingStream = null;
		try {
			settingStream = settingUrl.openStream();
			load(settingStream, isUseVariable);
		} catch (IOException e) {
			logger.error("Load setting error!", e);
			return false;
		} finally {
			FileUtil.close(settingStream);
		}
		return true;
	}

	/**
	 * 重新加载配置文件
	 */
	public void reload() {
		this.load(settingUrl);
	}

	/**
	 * 加载设置文件。 此方法不会关闭流对象
	 * 
	 * @param settingStream 文件流
	 * @param isUseVariable 是否使用变量（替换配置文件值中含有的变量）
	 * @return 加载成功与否
	 * @throws IOException
	 */
	public boolean load(InputStream settingStream, boolean isUseVariable) throws IOException {
		this.clear();
		BufferedReader reader = new BufferedReader(new InputStreamReader(settingStream, charset));

		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			// 跳过注释行和空行
			if (LangUtil.isEmpty(line) || line.startsWith(COMMENT_FLAG_PRE)) {
				continue;
			}

			// 记录分组名
			if (line.charAt(0) == GROUP_SURROUND[0] && line.charAt(line.length() - 1) == GROUP_SURROUND[1]) {
				this.group_cache = line.substring(1, line.length() - 1).trim();
			}

			String[] keyValue = line.split(ASSIGN_FLAG, 2);
			// 跳过不符合简直规范的行
			if (keyValue.length < 2) {
				continue;
			}

			String key = keyValue[0].trim();
			if (!LangUtil.isEmpty(this.group_cache)) {
				key = this.group_cache + "." + key;
			}
			String value = keyValue[1].trim();

			// 替换值中的所有变量变量（变量必须是此行之前定义的变量，否则无法找到）
			if (isUseVariable) {
				// 找到所有变量标识
				Set<String> vars = RegexUtil.findAll(reg_var, value, 0, new HashSet<String>());
				for (String var : vars) {
					// 查找变量名对应的值
					String varValue = this.get(RegexUtil.get(reg_var, var, 1));
					if (varValue != null) {
						// 替换标识
						value = value.replace(var, varValue);
					}
				}
			}
			put(key, value);
		}
		FileUtil.close(reader);
		this.group_cache = null;
		return true;
	}

	/**
	 * 设置变量的正则<br/>
	 * 正则只能有一个group表示变量本身，剩余为字符 例如 \$\{(name)\}表示${name}变量名为name的一个变量表示
	 * 
	 * @param regex 正则
	 */
	public void setVarRegex(String regex) {
		this.reg_var = regex;
	}

	/**
	 * 带有日志提示的get，如果没有定义指定的KEY，则打印debug日志
	 * 
	 * @param key 键
	 * @return 值
	 */
	public String getWithLog(String key) {
		String value = super.get(key);
		if (value == null) {
			logger.debug("No key define for [{}]!", key);
		}
		return value;
	}

	/**
	 * 获得指定分组的键对应值
	 * 
	 * @param key 键
	 * @param group 分组
	 * @return 值
	 */
	public String get(String key, String group) {
		String keyWithGroup = key;
		if (!LangUtil.isEmpty(group)) {
			keyWithGroup = group + "." + keyWithGroup;
		}
		return get(keyWithGroup);
	}

	/**
	 * 获取字符型型属性值
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public String getString(String key) {
		return getString(key, null);
	}

	/**
	 * 获取字符型型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public String getString(String key, String group) {
		return get(key, group);
	}

	/**
	 * 获取数字型型属性值
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public int getInt(String key) throws NumberFormatException {
		return getInt(key, null);
	}

	/**
	 * 获取数字型型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public int getInt(String key, String group) throws NumberFormatException {
		return Integer.parseInt(get(key, group));
	}

	/**
	 * 获取波尔型属性值
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public boolean getBool(String key) throws NumberFormatException {
		return getBool(key, null);
	}

	/**
	 * 获取波尔型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public boolean getBool(String key, String group) throws NumberFormatException {
		return Boolean.parseBoolean(get(key, group));
	}

	/**
	 * 获取long类型属性值
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public long getLong(String key) throws NumberFormatException {
		return getLong(key, null);
	}

	/**
	 * 获取long类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public long getLong(String key, String group) throws NumberFormatException {
		return Long.parseLong(get(key, group));
	}

	/**
	 * 获取char类型属性值
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public char getChar(String key) {
		return getChar(key, null);
	}

	/**
	 * 获取char类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public char getChar(String key, String group) {
		return get(key, group).charAt(0);
	}

	/**
	 * 获取double类型属性值
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public double getDouble(String key) throws NumberFormatException {
		return getDouble(key, null);
	}

	/**
	 * 获取double类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public double getDouble(String key, String group) throws NumberFormatException {
		return Double.parseDouble(get(key, group));
	}

	/**
	 * 设置值，无给定键创建之。设置后未持久化
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void setSetting(String key, Object value) {
		put(key, value.toString());
	}

	/**
	 * 持久化当前设置，会覆盖掉之前的设置<br>
	 * 持久化会不会保留之前的分组
	 * @param absolutePath 设置文件的绝对路径
	 */
	public void store(String absolutePath) {
		try {
			FileUtil.touch(absolutePath);
			OutputStream out = FileUtil.getOutputStream(absolutePath);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, charset));
			Set<java.util.Map.Entry<String, String>> entrySet = this.entrySet();
			for (java.util.Map.Entry<String, String> entry : entrySet) {
				writer.write(entry.getKey() + ASSIGN_FLAG + entry.getValue());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// 不会出现这个异常
		} catch (IOException e) {
			logger.error("存储设置时出现异常。", e);
		}
	}

	/**
	 * 存储当前设置，会覆盖掉以前的设置
	 * 
	 * @param path 相对路径
	 * @param clazz 相对的类
	 */
	public void store(String path, Class<?> clazz) {
		this.store(FileUtil.getAbsolutePath(path, clazz));
	}

	/**
	 * 将setting中的键值关系映射到对象中，原理是调用对象对应的set方法<br/>
	 * 只支持基本类型的转换
	 * 
	 * @param object 被调用的对象
	 * @throws SettingException
	 */
	public void settingToObject(String group, Object object) throws SettingException {
		try {
			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith("set")) {
					String field = LangUtil.getGeneralField(methodName);
					Object value = get(field, group);
					if (value != null) {
						Object castedValue = LangUtil.cast(method.getParameterTypes()[0], value);
						method.invoke(object, castedValue);
						logger.debug("Set {}=>{}", field, value);
					}
				}
			}
		} catch (Exception e) {
			throw new SettingException("转换设置至对象出现异常", e);
		}
	}

	/**
	 * 将setting中的键值关系映射到对象中，原理是调用对象对应的set方法<br/>
	 * 只支持基本类型的转换
	 * 
	 * @param object
	 * @throws SettingException
	 */
	public void settingToObject(Object object) throws SettingException {
		settingToObject(null, object);
	}
	/*--------------------------公有方法 end-------------------------------*/
}
