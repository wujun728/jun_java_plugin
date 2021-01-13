package book.string;

/**
 * 使用字符串类String
 */
public class UsingString {
	/**
	 * 查找子字符串
	 * @param str
	 */
	public static void testFindStr(String str) {
		// indexOf返回子字符串在字符串中的最先出现的位置，如果不存在，返回负数。
		System.out.println("str.indexOf(\"is\") = " + str.indexOf("is"));
		// 可以给indexOf方法设置参数，指定匹配的起始位置
		System.out.println("str.indexOf(\"is\", 4) = " + str.indexOf("is", 4));
		// lastIndexOf返回子字符串在字符串中的最后出现的位置，如果不存在，返回负数。
		System.out
				.println("str.lastIndexOf(\"is\") = " + str.lastIndexOf("is"));
		// 可以给lastIndexOf方法设置参数，指定匹配的结束位置
		System.out.println("str.lastIndexOf(\"is\", 1) = "
				+ str.lastIndexOf("is", 1));
	}
	/**
	 * 截取字符串
	 * @param str
	 */
	public static void testSubStr(String str) {
		// substring方法截取字符串，可以指定截取的起始位置和终止位置，
		// 默认终止位置为字符串末尾
		System.out.println("str.substring(2) = " + str.substring(2));
		System.out.println("str.substring(2, 9) = " + str.substring(2, 9));
	}
	/**
	 * 替换字符串
	 * @param str
	 */
	public static void testReplaceStr(String str) {
		// replace方法将字符串中的某个字符替换成另一个字符
		System.out.println("str.replace('i', 'I') = " + str.replace('i', 'I'));
		// replaceAll方法将字符串中的某个子字符串替换成另一个字符串
		System.out.println("str.replaceAll(\"is\", \"IS\") = "
				+ str.replaceAll("is", "IS"));
		// replaceFirst方法将字符串中的某个子字符串的第一个替换成另一个字符串
		System.out.println("str.replaceFirst(\"is\", \"IS\") = "
				+ str.replaceFirst("is", "IS"));
	}
	/**
	 * 转换大小写
	 * @param str
	 */
	public static void testToUpperOrLower(String str) {
		// toUpperCase方法将字符串全部变成大写形式
		System.out.println("str.toUpperCase() = " + str.toUpperCase());
		// toLowerCase方法将字符串全部变成小写形式
		System.out.println("str.toLowerCase() = " + str.toLowerCase());
	}
	/**
	 * 获取字符串中某个位置上的字符
	 * @param str
	 */
	public static void testCharAt(String str) {
		// charAt方法返回字符串中某个位置上的字符
		System.out.println("str.charAt(2) = " + str.charAt(2));
		// 将字符串转换成字符数组，数组长度为字符串的长度
		System.out.println(str.toCharArray());
	}
	/**
	 * 比较字符串的大小
	 * @param str
	 */
	public static void testCompareStr(String str) {
		// compareTo方法比较两个字符串的大小
		// 比较规则：首先比较第一个字符，根据字符编码比，如果字符编码比目标字符小，则返回负数，
		// 相等则继续比较第二个字符，大则返回正数。
		System.out.println("str.compareTo(\"I am in Beijing\") = "
				+ str.compareTo("I am in Beijing"));
		// compareToIgnoreCase在比较时忽略字符串的大小写，认为同一字符的大写和小写是相等的
		System.out.println("str.compareToIgnoreCase(\"I am in Beijing\") = "
				+ str.compareToIgnoreCase("I am in Beijing"));
	}
	/**
	 * 比较字符串是否相等
	 * @param str
	 */
	public static void testEqualsStr(String str) {
		// equals方法比较字符串的值想否相等
		System.out.println("str.equals(\"I am in Beijing\") = "
				+ str.equals("I am in Beijing"));
		// equalsIgnoreCase方法比较字符串的值时不去分大小写
		System.out.println("str.equalsIgnoreCase(\"I am in Beijing\") = "
				+ str.equalsIgnoreCase("I am in Beijing"));
		// startsWith方法判断字符串是否以某个子字符串开始
		System.out.println("str.startsWith(\"Th\") = " + str.startsWith("Th"));
		// endsWith方法判断字符串是否以某个子字符串结束
		System.out.println("str.endsWith(\"t!\") = " + str.endsWith("t!"));
	}
	
	/**
	 * String类也可以根据正则表达式操作字符串。
	 * 包括字符串匹配、替换和分割
	 */
	public static void testRegex(){
		String str = "aab        aaa  bb    ab";
		//该正则表达式表示包含任意多个英文字母或者空格。
		String pattern1 = "^[a-zA-Z| ]*$";
		System.out.println("用正则表达式匹配成功? " + str.matches(pattern1));
		
		//替换， 将字符串中的所有连续空格替换成一个空格
		System.out.println(str.replaceAll("\\s{2,}", " "));
		//将字符串中第一个连续的空格替换成一个空格
		System.out.println(str.replaceFirst("\\s{2,}", " "));
		
		//分割字符串，按空格分割，多个连续的空格当作一个空格
		String[] ss = str.split("\\s{1,}");
		System.out.println("用正则表达式按空格分割:");
		for (int i=0; i<ss.length; i++){
			System.out.println(ss[i]);
		}
		//限制分割后的数组的大小
		System.out.println("用正则表达式按空格分割，指定最大分割段数为3:");
		ss = str.split("\\s{1,}", 3);
		for (int i=0; i<ss.length; i++){
			System.out.println(ss[i]);
		}
	}

	public static void main(String[] args) {
		String str = "This is a String object!";
		System.out.println("str的值: " + str);
		
		UsingString.testFindStr(str);
		System.out.println();
		UsingString.testSubStr(str);
		System.out.println();
		UsingString.testReplaceStr(str);
		System.out.println();
		UsingString.testToUpperOrLower(str);
		System.out.println();
		UsingString.testCharAt(str);
		System.out.println();
		UsingString.testCompareStr(str);
		System.out.println();
		UsingString.testEqualsStr(str);
		System.out.println();
		UsingString.testRegex();
	}
}
