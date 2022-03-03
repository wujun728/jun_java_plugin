package com.jun.plugin.poi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.jun.plugin.poi.poiTemplate.Tag;
import com.jun.plugin.poi.poiTemplate.tags.ForeachTag;

/**
 * 标签处理工具类
 * @author Wujun
 *
 */
public class TagUtil {

	public static final String KEY_TAG = "#";

	private static Map<String, Tag> tagMap = new HashMap<String, Tag>();

	static {
		registerTag(ForeachTag.class);
	}

	/**
	 * 注册标签类
	 * @param clazz
	 */
	public static void registerTag(Class<?> clazz){
		Tag tag;
		try {
			tag = (Tag) clazz.newInstance();
			tagMap.put(tag.getTagName(), tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 注册指定包中的标签类
	 * @param tagPackage
	 */
	public static void registerTagPackage(String tagPackage){
		Collection<Class<?>> classs = ClassUtil.getClasses(tagPackage, true);
		for(Class<?> clazz : classs){
			if(Tag.class.isAssignableFrom(clazz)) {
				registerTag(clazz);
			}
		}
	}
	/**
	 * 获取字符串中对应的标签对象
	 * @param str
	 * @return
	 */
	public static Tag getTag(String str) {
		String tagName = null;
		if(str != null){
			int keytag = str.indexOf(KEY_TAG);
			if (keytag < 0)
				return null;
			if (!(keytag < str.length() - 1))
				return null;
			String tagRight = str.substring(keytag + 1, str.length());
			if (tagRight.startsWith(KEY_TAG))
				return null;
			StringTokenizer st = new StringTokenizer(str, " ");
			if (st.hasMoreTokens()) {
				tagName = st.nextToken();
			}
		}
		Tag tag = (Tag) tagMap.get(tagName);
		return tag;
	}
	
}
