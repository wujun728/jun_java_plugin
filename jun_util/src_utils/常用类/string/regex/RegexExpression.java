package book.string.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jdk1.4中加入了java.util.regex包提供对正则表达式的支持。
 * 而且Java.lang.String类中的replaceAll和split函数也是调用的正则表达式来实现的。
 */
public class RegexExpression {

	/**
	 * 利用正则表达式查找匹配字符串
	 */
	public static void testFind() {
		// ^符号匹配字符串的开头
		// 匹配以abc开头的字符串
		RegexExpression.find("abcdef", "^abc");
		RegexExpression.find("Aabc def", "^abc");
		System.out.println();

		// $符号匹配字符串的结尾
		// 匹配以def结尾的字符串
		RegexExpression.find("Aabcdef", "def$");
		RegexExpression.find("AabcdeF", "def$");
		// 如果同时使用^符号和$符号，将进行精确匹配
		RegexExpression.find("def", "^def$");
		RegexExpression.find("abcdefg", "^def$");
		System.out.println();

		// *符号匹配0个或多个前面的字符
		RegexExpression.find("a", "ab*");
		RegexExpression.find("ab", "ab*");
		RegexExpression.find("abbb", "ab*");
		System.out.println();

		// +符号匹配至少一个前面的字符
		RegexExpression.find("a", "ab+");
		RegexExpression.find("ab", "ab+");
		RegexExpression.find("abbb", "ab+");
		System.out.println();

		// ?符号匹配0个或1个前面的字符
		RegexExpression.find("a", "ab?c?");
		RegexExpression.find("ab", "ab?c?");
		RegexExpression.find("abc", "ab?c?");
		RegexExpression.find("abbcb", "ab?c?");
		System.out.println();

		// .符号匹配除换行符以外的任何字符，
		RegexExpression.find("a", ".");
		// .与+连用能匹配除换行符以外的所有字符串
		RegexExpression.find("dasf4566a`1345=-=4bsd", ".+");
		System.out.println();

		// x|y匹配"x"或"y"
		// abc|xyz 可匹配 "abc"或 "xyz"，而"ab(c|x)yz"匹配 "abcyz"和"abxyz"
		RegexExpression.find("x", "x|y");
		RegexExpression.find("y", "x|y");
		RegexExpression.find("abc", "abc|xyz");
		RegexExpression.find("xyz", "abc|xyz");
		RegexExpression.find("abc", "ab(c|x)yz");
		RegexExpression.find("abcyz", "ab(c|x)yz");
		System.out.println();

		// {n}匹配恰好n次（n为非负整数）前面的字符
		RegexExpression.find("aa", "a{3}");
		RegexExpression.find("aaa", "a{3}");
		System.out.println();

		// {n,}匹配至少n次（n为非负整数）前面的字符
		RegexExpression.find("aaa", "a{3,}");
		RegexExpression.find("aaaaa", "a{3,}");
		System.out.println();

		// {m,n}匹配至少m个，至多n个前面的字符
		RegexExpression.find("aaa", "a{3,4}");
		RegexExpression.find("aaaa", "a{3,4}");
		RegexExpression.find("aaaaa", "a{3,4}");
		System.out.println();

		// [xyz]表示一个字符集，匹配括号中字符的其中之一
		RegexExpression.find("a", "[abc]");
		RegexExpression.find("b", "[abc]");
		RegexExpression.find("c", "[abc]");
		RegexExpression.find("ab", "[abc]");
		System.out.println();

		// [^xyz]表示一个否定的字符集。匹配不在此括号中的任何字符
		RegexExpression.find("a", "[^abc]");
		RegexExpression.find("x", "[^abc]");
		RegexExpression.find("8", "[^abc]");
		System.out.println();

		// [a-z]匹配从"a"到"z"之间的任何一个小写字母字符
		RegexExpression.find("c", "[b-d]");
		RegexExpression.find("f", "[b-d]");
		RegexExpression.find("$", "[b-d]");
		System.out.println();

		// [^a-z]表示某个范围之外的字符，匹配不在指定范围内的字符
		RegexExpression.find("f", "[b-d]");
		RegexExpression.find("b", "[b-d]");
		System.out.println();

		// [a-zA-Z] a到z或A到Z
		RegexExpression.find("B", "[a-cA-F]");
		RegexExpression.find("G", "[a-cA-F]");
		System.out.println();

		// [a-z-[bc]] a到z，除了b和c
		RegexExpression.find("c", "[a-z-[bcd]]");
		RegexExpression.find("e", "[a-z-[bcd]]");
		RegexExpression.find("f", "[a-z-[e-x]]");
		System.out.println();

		// 特殊字符，\n换行符；\f分页符；\r回车；\t制表符
		RegexExpression.find("\n", "\n");
		RegexExpression.find("\f", "\f");
		RegexExpression.find("\r", "\r");
		RegexExpression.find("\t", "\t");
		System.out.println();

		// \\表示转义符\，在使用时要用\\\\代表\\，\\代表\。
		RegexExpression.find("\\", "\\\\");
		System.out.println();

		// \s 任何白字符，包括空格、制表符、分页符等。等价于"[\f\n\r\t]"
		// 使用\s时前面再加一个\
		RegexExpression.find("\n", "\\s");
		RegexExpression.find("\f", "\\s");
		RegexExpression.find("\r", "\\s");
		RegexExpression.find("\t", "\\s");
		System.out.println();

		// \S 任何非空白的字符。等价于"[^\f\n\r\t]"
		// 使用\s时前面再加一个\
		RegexExpression.find("\n", "\\S");
		RegexExpression.find("\f", "\\S");
		RegexExpression.find("a", "\\S");
		RegexExpression.find("9", "\\S");
		System.out.println();

		// \w 任何单词字符，包括字母和下划线。等价于"[A-Za-z0-9_]"
		// 使用时用\\w
		RegexExpression.find("a", "\\w");
		RegexExpression.find("9", "\\w");
		RegexExpression.find("X", "\\w");
		RegexExpression.find("_", "\\w");
		System.out.println();

		// \W 任何非单词字符。等价于"[^A-Za-z0-9_]"
		// 使用时用\\W
		RegexExpression.find("a", "\\W");
		RegexExpression.find("9", "\\W");
		RegexExpression.find("$", "\\W");
		RegexExpression.find("#", "\\W");
		System.out.println();

		// \d匹配一个数字字符，等价于[0-9]
		RegexExpression.find("6", "\\d");
		RegexExpression.find("9", "\\d");
		RegexExpression.find("A", "\\d");
		System.out.println();

		// \D匹配一个非数字字符，等价于[^0-9]
		RegexExpression.find("%", "\\D");
		RegexExpression.find("$", "\\D");
		RegexExpression.find("A", "\\D");
		RegexExpression.find("8", "\\D");
		System.out.println();

		// \b匹配单词的结尾
		RegexExpression.find("love", "ve\\b");
		RegexExpression.find("very", "ve\\b");
		System.out.println();

		// \B匹配单词的开头
		RegexExpression.find("love", "ve\\B");
		RegexExpression.find("very", "ve\\B");
		System.out.println();
	}
	/**
	 * 利用正则表达式查找匹配字符串。
	 * @param str   待匹配的字符串
	 * @param regex 正则表达式
	 * @return
	 */
	public static boolean find(String str, String regex) {
		// 将正则表达式编译成一个Pattern
		Pattern p = Pattern.compile(regex);
		// 创建一个Matcher
		Matcher m = p.matcher(str);
		// Matcher的find方法用来查找或者匹配，只要能找到满足正则表达式的子串，就返回true
		boolean b = m.find();
		System.out.println("\"" + str + "\" 匹配正则表达式 \"" + regex + "\" ?  " + b);
		return b;
	}
	/**
	 * 测试利用正则表达式 精确匹配 字符串
	 */
	public static void testMatch() {
		RegexExpression.match("abcdef", "^abc");
		RegexExpression.match("Aabc def", "^abc");
		RegexExpression.match("Aabcdef", "def$");
		RegexExpression.match("AabcdeF", "def$");
		RegexExpression.match("def", "^def$");
	}
	/**
	 * 精确匹配字符串和正则表达式，所谓精确匹配是字符串的每个字符都满足正则表达式
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean match(String str, String regex) {
		// 将正则表达式编译成一个Pattern
		Pattern p = Pattern.compile(regex);
		// 创建一个Matcher
		Matcher m = p.matcher(str);
		boolean b = m.matches();// 精确匹配
		System.out.println("\"" + str + "\" 精确匹配正则表达式 \"" + regex + "\" ?  "
				+ b);
		return b;
	}
	/**
	 * 测试利用正则表达式替换字符串
	 */
	public static void testReplace() {
		// 将字符串中重复的空格替换为一个空格
		RegexExpression.replace("a  a    a   a", " {2,}", " ");
		RegexExpression.replace("abcad a", "a", "x");
	}
	/**
	 * 利用正则表达式替换字符串
	 * @param str    待替换的字符串
	 * @param regex  正则表达式
	 * @param newStr 用来替换的字符串
	 * @return
	 */
	public static String replace(String str, String regex, String newStr) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		// 用新串替换所有满足正则表达式的子串
		String s = m.replaceAll(newStr);
		System.out.println("\"" + str + "\" 中匹配正则表达式 \"" + regex + "\" 部分被 \""
				+ newStr + "\" 替换后: " + s);
		return s;
	}
	/**
	 * 利用正则表达式分割字符串
	 */
	public static void testSplit() {
		// 按空格分割字符串，空格符可以是连续的多个。
		System.out.println("RegexExpression.split(\"ab  aba a    bbc bc\", \" +\", 5): result");
		RegexExpression.outputStrArray(RegexExpression.split(
				"ab  aba a    bbc bc", " +", 5));
		// 按照字母b分割字符串
		System.out
				.println("RegexExpression.split(\"ab  aba a    bbc bc\", \"b\", 5): result");
		RegexExpression.outputStrArray(RegexExpression.split(
				"ab  aba a    bbc bc", "b", 5));
	}
	/**
	 * 使用正则表达式分割字符串
	 * @param str   待分割的字符串
	 * @param regex 正则表达式
	 * @param count 最终被分成的段数的最大值
	 * @return
	 */
	public static String[] split(String str, String regex, int count) {
		Pattern p = Pattern.compile(regex);
		// 按照符合正则表达式的子串分割字符串
		return p.split(str, count);
	}
	/**
	 * 输出字符串数组
	 * @param array
	 */
	public static void outputStrArray(String[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				System.out.println(i + ": " + array[i]);
			}
		}
	}
	public static void main(String[] args) {
		RegexExpression.testFind();
		RegexExpression.testMatch();
		System.out.println();
		RegexExpression.testReplace();
		System.out.println();
		RegexExpression.testSplit();
		System.out.println();
		// 查找并替换， 替换换前2个满足正则表达式的子串
		Pattern p = Pattern.compile("a+");
		Matcher m = p.matcher("bba bb aaa bbabb bab");
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while ((m.find()) && (i < 2)) {
			// 将字符串中的匹配的部分替换，
			// 并且两次匹配之间（包括被匹配的部分）的字符串追加到sb后
			m.appendReplacement(sb, "XX");
			i++;
		}
		// 将字符串中没有进行匹配的部分全部追加到sb后面
		m.appendTail(sb);
		System.out.println(sb.toString());
	}
	/**
	 * 在程序中使用正则表达式的步骤一般如下： 1. 建立Pattern对象，通过静态方法Pattern.compile(); 2.
	 * 取得Matcher对象，通过pattern.matcher(CharSequence charSequence); 3.
	 * 调用Matcher对象的相关查找方法。
	 * java.lang.CharSequence接口是在JDK1.4版本中被添加进来的，它为不同种类的char序列
	 * 提供了统一的只读访问。实现的类有String, StringBuffer, java.nio.CharBuffer
	 * Matcher提供几个不同的查找方法，比String类的matches()方法更灵活，如下：
	 * match() 使用整个字符串与相关模式相比较，和String的matches()差不多
	 * lookingAt() 从字符串开头的地方与相关模式比较
	 * find() 匹配字符串，没有必要从字符串的第一个字符开始，如果前一个操作匹配， 而且匹配器没有重置的话，则从不匹配的第一个字符开始。
	 * 上面每个方法都返回boolean类型，返回true则意味的匹配，返回false则不匹配。
	 * 要检测给定的String是否匹配给定的模式，只须下面这样就可以了：
	 * Matcher m = Pattern.compile(yourPattern).matcher(yourString);
	 * if (m.find()) { System.out.println(“match”); } else {
	 * System.out.println(“no match”); }
	 */
}