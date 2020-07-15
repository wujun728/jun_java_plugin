package com.jun.plugin.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;


/**
 * @ClassName: LoadPopertiesFile
 * @Description: TODO
 * @author
 * @date 2011-12-28
 * @version V1.0
 */
public final class LoadPopertiesFile {

	private LoadPopertiesFile() {
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
		InputStream is = LoadPopertiesFile.class.getClassLoader()
				.getResourceAsStream(filePath);

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
//			Constants.loadSqlMap.put(key, value);
		}
	}
}