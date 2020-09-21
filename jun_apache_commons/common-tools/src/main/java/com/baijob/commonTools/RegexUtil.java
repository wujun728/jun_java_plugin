package com.baijob.commonTools;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则相关工具类
 * 
 * @author 路小磊 luxiaolei@baijob.com
 */
public class RegexUtil {

	private RegexUtil() {
		//阻止实例化
	}

	/**
	 * 获得匹配的字符串
	 * 
	 * @param regex 匹配的正则
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(String regex, String content, int groupIndex) {
		Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(content);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}

	/**
	 * 删除匹配的内容
	 * 
	 * @param regex 正则
	 * @param content 被匹配的内容
	 * @return 删除后剩余的内容
	 */
	public static String delFirst(String regex, String content) {
		return content.replaceFirst(regex, "");
	}

	/**
	 * 删除正则匹配到的内容之前的字符 如果没有找到，则返回原文
	 * 
	 * @param regex 定位正则
	 * @param content 被查找的内容
	 * @return 删除前缀后的新内容
	 */
	public static String delPreLocation(String regex, String content) {
		Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(content);
		if (matcher.find()) {
			return content.substring(matcher.end(), content.length());
		}
		return content;
	}

	/**
	 * 取得内容中匹配的所有结果
	 * 
	 * @param regex 正则
	 * @param content 被查找的内容
	 * @param group 正则的分组
	 * @param collection 返回的集合类型
	 * @return 结果集
	 */
	@Deprecated
	public static <T extends Collection<String>> T gets(String regex, String content, int group, T collection) {
		while (true) {
			String result = get(regex, content, group);
			if (result == null) break;
			collection.add(result);
			content = delPreLocation(regex, content);
		}
		return collection;
	}
	
	/**
	 * 取得内容中匹配的所有结果
	 * 
	 * @param regex 正则
	 * @param content 被查找的内容
	 * @param group 正则的分组
	 * @param collection 返回的集合类型
	 * @return 结果集
	 */
	public static <T extends Collection<String>> T findAll(String regex, String content, int group, T collection) {
		Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(content);
		while(matcher.find()){
			collection.add(matcher.group(group));
		}
		return collection;
	}

	/**
	 * 从字符串中获得第一个整数
	 * 
	 * @param StringWithNumber 带数字的字符串
	 * @return 整数
	 */
	public static int getFirstNumber(String StringWithNumber) {
		return Integer.parseInt(get("\\d+", StringWithNumber, 0));
	}
	
	/**
	 * 判断该字符串是否是IPV4地址
	 * 
	 * @param ip IP地址
	 * @return 是否是IPV4
	 */
	public static boolean isIpv4(String ip) {
		if(LangUtil.isEmpty(ip)){
			return false;
		}
		String regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
		return Pattern.matches(regex, ip);
	}
}
