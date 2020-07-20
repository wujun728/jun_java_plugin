package klg.common.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class AppProperties {
	private Properties props = null;

	public AppProperties(String fileName) {
		super();
		props = new Properties();
		try {
			// 读取propertiesUtil类的配置
			// 利用反射加载类信息，获取配置文件的文件流，并指点编码格式
			props.load(new InputStreamReader(AppProperties.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = props.getProperty(key.trim());
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return value.trim();
	}

	/**
	 * 提供默认值的方法，如果在配置文件中找不到对应的key,就返回参数中的默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		String value = props.getProperty(key.trim());
		if (StringUtils.isBlank(value)) {
			value = defaultValue;
		}
		return value.trim();
	}

	public String[] getStringArray(String key, String separatorChars) {
		String propValue = getProperty(key);
		if (propValue == null) {
			return null;
		}

		return StringUtils.split(propValue, separatorChars);
	}

	public int[] getIntArray(String key, String separatorChars) {
		String[] temp = getStringArray(key, separatorChars);
		int[] target = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			target[i] = Integer.parseInt(temp[i]);
		}
		return target;
	}

	public static void main(String[] args) {
		AppProperties appProperties = new AppProperties("");
		System.out.println(Arrays.toString(appProperties.getStringArray("kline.highPointDaysArray", ",")));
		System.out.println(Arrays.toString(appProperties.getIntArray("kline.highPointDaysArray", ",")));
	}

}
