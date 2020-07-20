package klg.common.utils;

import java.util.Arrays;
import java.util.Comparator;

public class StringTools {
	
	public static final String ASC="asc";
	public static final String DESC="desc";

	/**
	 * 根据字符串的值排序
	 * 
	 * @param arr
	 * @param method
	 *            升序或降序 asc desc
	 */
	public static void sortByValue(String[] arr, final String method) {
		Arrays.sort(arr, new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (method.equals("desc"))
					return o2.compareTo(o1);
				else if (method.equals("asc"))
					return o1.compareTo(o2);
				else
					return 0;
			}
		});
	}

	/**
	 * 根据字符串长度排序
	 * 
	 * @param arr
	 * @param method
	 *            升序或降序 asc desc
	 */
	public static void sortByLength(String[] arr, final String method) {
		Arrays.sort(arr, new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (method.equals("desc"))
					return o2.length() - o1.length();// 降序
				else if (method.equals("asc"))
					return o1.length() - o2.length();// 升序
				else
					return 0;
			}
		});
	}
	
	// 首字母转小写
	public static String lowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String upperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	public static String replaceLast(String s,String regex,String replacement){
		String target="";
		int index=s.lastIndexOf(regex);//找到最后一个要替换的字符位置
		String temp1=s.substring(0, index);//将字符串分段
		String temp2=s.substring(index);		 
		String temp3=temp2.replaceFirst(regex, replacement);//替换
		target=temp1+temp3; //拼接
		return target;
	}
}
