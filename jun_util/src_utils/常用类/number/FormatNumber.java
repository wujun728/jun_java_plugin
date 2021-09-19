package book.number;

import java.text.DecimalFormat;
/**
 * 格式化数字的输出
 */
public class FormatNumber {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat();
		double data = 1203.405607809;
		System.out.println("格式化之前的数字: " + data);
		// 在格式化的时候会自动进行舍入，舍入模式是：
		// 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。

		// 模式中的"."表示小数分隔符
		// 模式中的"0"表示如果该位存在字符，则显示字符，如果不存在，则显示0
		String pattern = "0.0";// 显示格式
		df.applyPattern(pattern);// 将格式应用于格式化器
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));// 1203.4
		pattern = "00000.000 kg";// 可以在模式最后加自己想要的任何字符，比如单位
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));// 01203.406 kg

		// 模式中的"#"表示如果该位存在字符，则显示字符，如果不存在，则不显示。
		pattern = "##000.000 kg";// 注意#只能出现在模式的两头，不能在0中间
		// 错误！ pattern = "##00#.#0"
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));// 1203.406 kg

		// 模式中的"-"表示输出为负数，要放在最前面
		pattern = "-000.000";
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));

		// 模式中的","在数字中添加逗号，方便读数字
		pattern = "-0,000.0#";
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));

		// 模式中的"E"表示输出为指数，"E"之前的字符串是底数的格式，
		// "E"之后的是字符串是指数的格式
		pattern = "0.00E000";
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));

		// 模式中的"%"表示乘以100并显示为百分数，要放在最后。
		pattern = "0.00%";
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));

		// 模式中的"\u2030"表示乘以1000并显示为千分数，要放在最后。
		pattern = "0.00\u2030";
		df.applyPattern(pattern);
		System.out.println("采用pattern: " + pattern + "格式化之后: "
				+ df.format(data));
	}
}
