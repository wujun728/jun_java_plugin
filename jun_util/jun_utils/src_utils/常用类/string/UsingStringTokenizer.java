package book.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * StringTokenizer主要用来根据分隔符来分割字符串。
 */
public class UsingStringTokenizer {

	/**默认分隔符*/
	public final static String DELIM = ",";
	
	public static String[] process(String line){
		return process(line, DELIM, false);
	}

	public static String[] process(String line, String delim){
		return process(line, delim, false);
	}
	/**
	 * 用StringTokenizer分割字符串
	 * @param line	待分割的字符串
	 * @param delim	分割符
	 * @param returnDelims	是否返回分隔符，默认为false。
	 * @param maxfields 分割后的最大的段数
	 * @return		被分割后的字符串数组
	 */
	public static String[] process(String line, String delim, boolean returnDelims) {

		List results = new ArrayList();
		//新建一个StringTokenizer对象
		StringTokenizer st = new StringTokenizer(line, delim, returnDelims);
		//循环，如果字符串中还有分隔符，则继续
		while (st.hasMoreTokens()) {
			//取上一个分隔符到下一个分隔符之间的字符串
			String s = st.nextToken();
			//将中间的字符串添加到结果列表中
			results.add(s);
		}
		return (String[])results.toArray(new String[0]);
	}
	/**
	 * 输出分割结果
	 * @param input
	 * @param outputs
	 */
	public static void printResults(String input, String[] outputs) {
		System.out.println("Input: " + input);
		for (int i = 0; i < outputs.length; i++){
			System.out.println("Output " + i + " was: " + outputs[i]);
		}
	}

	public static void main(String[] a) {
		printResults("A|B|C|D", process("A|B|C|D", "|"));
		printResults("A||C|D", process("A||C|D", "|", true));
		printResults("A|||D|E", process("A|||D|E", "|", false));
		printResults("A;bD;|E;FG", process("A;bD;|E;FG", ";"));
		printResults("A;bD;|E;FG;dfxxf;ert", process("A;bD;|E;FG;dfxxf;ert", ";", false));
	}
}
