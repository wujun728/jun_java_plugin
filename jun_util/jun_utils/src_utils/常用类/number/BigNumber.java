package book.number;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 第1.4节的计算Factorial的类中，我们限制了用户输入的n必须小于18，原因是，当n等于18时，
 * n!的值已经超过了范围最大的基本类型long能够表示的范围了，也就是说使用Java的基本数据类型在进行运算时会存在溢出问题。
 * 
 * 同样，在进行小数运算时，也会出现不精确的问题。请看下面一段代码：
 * code
 * 执行结果让人迷惑。
 * Java中的简单浮点数类型float和double不能够进行精确的运算，只能用来做科学计算或者是工程计算。
 * 不光是Java，在其它很多编程语言中也有这样的问题。在大多数情况下，计算的结果是准确的，
 * 但是多试几次（可以做一个循环）就可以试出类似上面的错误。
 * 这个问题相当严重，如果你有9.999999999999元，你的计算机是不会认为你可以购买10元的商品的。
 * 
 * 为了解决Java基本数据类型在运算时会出现的溢出和计算不精确的问题。
 * Java 提供了两个类BigInteger和BigDecimal，专门用于进行高精度运算
 * 凡是能用int 或float 做的事情，用BigInteger和BigDecimal也可以做，
 * 只是必须换用方法调用，而不是使用运算符。
 * 
 * 高精度整数BigInteger
 * BigInteger支持任意精度的整数，也就是说我们可精确表示任意大小的整数值；同时在运算过程中不会丢失任何信息；
 * 
 * 高精度浮点数BigDecimal
 * 它可以表示任意精度的小数，并对它们进行计算。
 * 由于 BigDecimal 对象是不可变的，这些方法中的每一个都会产生新的 BigDecimal 对象。
 * 因此，因为创建对象的开销，BigDecimal 不适合于大量的数学计算，但设计它的目的是用来精确地表示小数。
 */
public class BigNumber {

	//默认除法运算精度,即保留小数点多少位 
	private static final int DEFAULT_DIV_SCALE = 10;

	//这个类不能实例化 
	private BigNumber() {
	}

	/** 
	 * 提供精确的加法运算。 
	 * @param v1 被加数 
	 * @param v2 加数 
	 * @return 两个参数的和 
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.add(b2)).doubleValue();
	}

	/** 
	 * 提供精确的减法运算。 
	 * @param v1 被减数 
	 * @param v2 减数 
	 * @return 两个参数的差 
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.subtract(b2)).doubleValue();
	}

	/** 
	 * 提供精确的乘法运算。 
	 * @param v1 被乘数 
	 * @param v2 乘数 
	 * @return 两个参数的积 
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.multiply(b2)).doubleValue();
	}

	/** 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
	 * 小数点以后多少位，以后的数字四舍五入。 
	 * @param v1 被除数 
	 * @param v2 除数 
	 * @return 两个参数的商 
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	 * 定精度，以后的数字四舍五入。 
	 * @param v1 被除数 
	 * @param v2 除数 
	 * @param scale 表示需要精确到小数点以后几位。 
	 * @return 两个参数的商 
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			System.err.println("除法精度必须大于0!");
			return 0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)).doubleValue();
	}

	/**
	 * 计算Factorial阶乘！
	 * @param n		任意大于等于0的int
	 * @return     n!的值
	 */
	public static BigInteger getFactorial(int n) {
		if (n < 0) {
			System.err.println("n必须大于等于0！");
			return new BigInteger("-1");
		} else if (n == 0) {
			return new BigInteger("0");
		}
		//将数组换成字符串后构造BigInteger
		BigInteger result = new BigInteger("1");
		for (; n > 0; n--) {
			//将数字n转换成字符串后，再构造一个BigInteger对象，与现有结果做乘法
			result = result.multiply(new BigInteger(new Integer(n).toString()));
		}
		return result;
	}

	public static void main(String[] args) {

		//		如果我们编译运行下面这个程序会看到什么？
		System.out.println(0.05 + 0.01);
		System.out.println(1.0 - 0.42);
		System.out.println(4.015 * 100);
		System.out.println(123.3 / 100);
		//		0.060000000000000005
		//		0.5800000000000001
		//		401.49999999999994
		//		1.2329999999999999

		//计算阶乘，可以将n设得更大
		int n = 30;
		System.out.println("计算n的阶乘" + n + "! = " + BigNumber.getFactorial(n));

		//用double构造BigDecimal
		BigDecimal bd1 = new BigDecimal(0.1);
		System.out.println("(bd1 = new BigDecimal(0.1)) = " + bd1.toString());
		//用String构造BigDecimal
		BigDecimal bd2 = new BigDecimal("0.1");
		System.out.println("(bd2 = new BigDecimal(\"0.1\")) = "
				+ bd2.toString());

		BigDecimal bd3 = new BigDecimal("0.10");
		//equals方法比较两个BigDecimal对象是否相等，相等返回true，不等返回false
		System.out.println("bd2.equals(bd3) = " + bd2.equals(bd3));//false
		//compareTo方法比较两个BigDecimal对象的大小，相等返回0，小于返回-1，大于返回1。
		System.out.println("bd2.compareTo(bd3) = " + bd2.compareTo(bd3));//0

		//进行精确计算
		System.out.println("0.05 + 0.01 = " + BigNumber.add(0.05, 0.01));
		System.out.println("1.0 - 0.42 = " + BigNumber.add(1.0, 0.42));
		System.out.println("4.015 * 100 =" + BigNumber.add(4.015, 100));
		System.out.println("123.3 / 100 = " + BigNumber.add(123.3, 100));

		/**
		 * （1）BigInteger和BigDecimal都是不可变(immutable)的，在进行每一步运算时，都会产生一个新的对象，由于创建对象会引起开销，
		 * 它们不适合于大量的数学计算，应尽量用long,float,double等基本类型做科学计算或者工程计算。
		 * 设计BigInteger和BigDecimal的目的是用来精确地表示大整数和小数，使用于在商业计算中使用。
		 * （2）BigDecimal有4个够造方法，其中的两个用BigInteger构造，另一个是用double构造，还有一个使用String构造。
		 * 应该避免使用double构造BigDecimal，因为：有些数字用double根本无法精确表示，传给BigDecimal构造方法时就已经不精确了。
		 * 比如，new BigDecimal(0.1)得到的值是0.1000000000000000055511151231257827021181583404541015625。
		 * 使用new BigDecimal("0.1")得到的值是0.1。因此，如果需要精确计算，用String构造BigDecimal，避免用double构造，尽管它看起来更简单！ 
		 * （3）equals()方法认为0.1和0.1是相等的，返回true，而认为0.10和0.1是不等的，结果返回false。
		 * 方法compareTo()则认为0.1与0.1相等，0.10与0.1也相等。所以在从数值上比较两个BigDecimal值时，应该使用compareTo()而不是 equals()。
		 * （4）另外还有一些情形，任意精度的小数运算仍不能表示精确结果。例如，1除以9会产生无限循环的小数 .111111...。
		 * 出于这个原因，在进行除法运算时，BigDecimal可以让您显式地控制舍入。
		 */

	}

}