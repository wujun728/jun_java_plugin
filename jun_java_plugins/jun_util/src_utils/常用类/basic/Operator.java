package book.basic;

public class Operator {
	/**
	 * 计算运算符: +; -; *; /;
	 */
	private void computeOperator() {
		int a = 8;
		int b = 5;
		// 对于除法运算，根据基本类型的自动转换规则，当除数和被除数都是整数时，得到的结果也是整数。
		// 因此 8/5得到的是1，而不是1.6
		int f = a / b;
		double g = a / b;
		System.out.println("(f = a / b) = " + f + "; (g = a / b) = " + g);
		// 只要除数和被除数有一个为double或者float，结果就不同了，8/5.0得到是1.6
		double h = a / (b * 1.0d);
		float i = a / (b * 1.0f);
		System.out.println("(h = a / (b * 1.0d)) = " + h + "; (i = a / (b * 1.0f)) = " + i);
	}

	/**
	 * 比较运算符: ==; <; >; !=; <=; >=;
	 */
	private void compareOperator() {
		int a = 5;
		int b = 10;
		System.out.println("(a == b) = " + (a == b)); 
		System.out.println("(a < b) = " + (a < b)); 
		System.out.println("(a > b) = " + (a > b)); 
		System.out.println("(a != b) = " + (a != b)); 
		System.out.println("(a <= b) = " + (a <= b)); 
		System.out.println("(a >= b) = " + (a >= b)); 
		// 判断两个数字是否相等，要用"=="而不是"="，前者是比较运算符，后者是赋值符号
		System.out.println("(a = b) = " + (a = b)); // 10
	}

	/**
	 * 位运算符: &; |; ^; ~; >>; >>>; <<;
	 */
	private void bitOperator() {
		byte a = 23; // "00010111"
		byte b = 26; // "00011010"

		// 按位与, 两个运算数都为1时，结果为1，其余结果为0
		int c = a & b; // "00010010" = 18
		System.out.println("(c = a & b) = " + c);
		// 按位或，两个运算数都为0时，结果为0，其余结果为1
		int d = a | b; // "00011111" = 31
		System.out.println("(d = a | b) = " + d);
		// 按位异或，两个运算数相同时结果为0，不同时结果为1
		int e = a ^ b; // "00001101" = 13
		System.out.println("(e = a ^ b) = " + e);
		// 按位非，0变成1，1变成0
		int f = ~a; // "11101000" = -24,
		// 注意，Java中采用补码存储数字，对于整数，原码与补码一致，
		// 对于负数，符号位不变，将原码取反然后加1，得到补码，补码"11101000"对应的原码是"10011000"即-24
		System.out.println("(f = ~a) = " + f);
		// 右移，左边空出位以符号位填充
		int g = a >> 1;// "00001011" = 11
		System.out.println("(g = a >> 1) = " + g);
		// 右移，左边空出位以0填充
		int h = a >>> 1;// "00001011" = 11
		System.out.println("(h = a >>> 1)" + h);
		// 对负数操作时，>>和>>>得到结果会不一样
		System.out.println("(-24 >> 1) = " + (-24 >> 1) + "\t (-24 >>> 1) = " + (-24 >>> 1));
		// 左移
		int i = a << 1; // "00101110" = 46
		System.out.println("(a << 1) = " + i);

		// 可以发现，右移1位相当于运算数除以2，左移1位相当于运算数乘以2，
		// 实质上，右移n位，相当于运算数除以2的n次方，左移n位，相当于运算数乘以2的n次方，读者可以试验。
		// 在进行乘除法操作时，开发者要有意识的利用这个特点，以提高运算速度。
	}

	/**
	 * 布尔运算符: &&; ||; !; &; |; ^;
	 */
	private void booleanOperator() {
		boolean b1 = true;
		boolean b2 = true;
		boolean b3 = false;

		// &&运算符：对操作数进行与运算，当所有操作数都为true时，结果为true，否则结果为false。
		if (b1 && b2 && b3) {
			System.out.println("变量b1, b2, b3的值都为true");
		} else {
			System.out.println("变量b1, b2, b3中至少有一个的值为false");
		}
		// 注意&&是短路与，意思是：当对操作数的表达式进行从左到右运算时，若遇到有操作数的值为false，则结束运算，将结果置为false。
		int i = 2;
		int j = 0;
		if (b3 && ((j = i) == 2)) {
		}
		System.out.println("j = " + j); 
		if (b1 && ((j = i) == 2)) {
		}
		System.out.println("j = " + j); 

		// ||运算符：对操作数进行或运算，当所有操作数都为false时，结果为false，否则结果为true。
		if (b1 || b2 || b3) {
			System.out.println("变量b1, b2, b3中至少有一个的值为true");
		} else {
			System.out.println("变量b1, b2, b3的值都为false");
		}
		// 同样，||是短路或，意思是：当对操作数的表达式进行从左到右运算时，若遇到有操作数的值为true，就结束运算，将结果置为true。
		if (b1 || ((j = j - 1) == 1)) {
		}
		System.out.println("j = " + j);
		if (b3 || ((j = j - 1) == 1)) {
		}
		System.out.println("j = " + j);

		// !运算符：对操作数的值进行取反运算，操作数为true，取反为false。
		if (!b1) {
			System.out.println("变量b1 为 false，因为b1取反后的值为true");
		} else {
			System.out.println("变量b1 为 true，因为b1取反后的值为false");
		}

		// &运算符，和&&一样，对操作数做与操作，不同的是它不是短路的，它会运算完所有的操作数表达式
		if (b3 & ((j = i) == 2)) {
			System.out.println("b3 & ((j=i) == 2): true");
		} else {
			System.out.println("b3 & ((j=i) == 2): false");
		}
		System.out.println("j = " + j); 
		if (b1 & ((j = j - 1) == 0)) {
		}
		System.out.println("j = " + j);

		// |运算符，和||一样，对操作数进行或操作，但它不是短路的。
		if (b1 | ((j = i) == 2)) {
			System.out.println("b1 | ((j=i) == 2): true");
		} else {
			System.out.println("b1 | ((j=i) == 2): false");
		}
		System.out.println("j = " + j); 
		if (b3 | ((j = j - 1) == 1)) {
		}
		System.out.println("j = " + j); 

		// ^运算符，对操作数做异或操作，相同为false，不同为true
		if (b1 ^ b2) {
			System.out.println("变量b1，b2的值不同！");
		} else {
			System.out.println("变量b1，b2的值一样！");
		}

		// 注意：开发人员应该尽量使用短路与&&或者短路或||，而少用&和|操作符，因为，它们对整个条件表达式的计算的结果是一样的。
		// 使用&&和||可以减少运行时间。尽量不要在条件表达式中进行赋值等运算，可以将这类语句放到if之外。
		// j = i;
		// if (b3 && (j==2)){
		// }
	}

	/**
	 * 赋值运算符: =; +=; -=; *=; /=; &=; |=; ^=;
	 */
	private void evaluateOperator() {
		// =是最常用的赋值运算符
		int i = 5;// 将变量i的值赋为5
		// +=运算符是先将运算符左边的操作符的值加上右边的操作数的值，将结果再赋值给运算符左边的操作符
		i += 3;// 等价于 i = i + 3;
		i -= 3;// 等价于i = i - 3;
		i *= 3;// 等价于i = i * 3;
		i /= 3;// 等价于i = i / 3;
		i &= 3;// 等价于i = i & 3
		System.out.println("(i &= 3) = " + i);
		i |= 3;// 等价于i = i | 3
		System.out.println("(i |= 3) = " + i);
		i ^= 3;// 等价于i = i ^ 3
		System.out.println("(i ^= 3) = " + i);
	}

	/**
	 * 其他运算符: 三元运算符; ++; --; -;
	 * 
	 */
	private void otherOperator() {
		int i = 5;
		// ++是将操作数加1
		// ++i表示先将i加1后再进行运算
		if (i++ == 5) {
			System.out.println("表达式(i++ == 5)的值为true");
		} else {
			System.out.println("表达式(i++ == 5)的值为false");
		}
		System.out.println("i = " + i);
		// i++表示先进行运算，再将i的值加1
		i = 5;
		if (++i == 5) {
			System.out.println("表达式(++i == 5)的值为true");
		} else {
			System.out.println("表达式(++i == 5)的值为false");
		}
		System.out.println("i = " + i);
		// --将操作数减1，同样，--i是先将i减1，再进行运算；i++是先进行运算，再将i减1
		i--;
		--i;

		// 可以用三目运算符替代简单的if语句
		// 格式是： x = a ? b : c；如果a的值为true，则将b的值赋予x，否则将c的值赋予x。
		int x = (i >= 0) ? i : -i; //将一个减号-和数字连用，是取数的负数
		System.out.println("i的绝对值为: " + x); 
	}

	public static void main(String[] args) {
		Operator test = new Operator();
		System.out.println("计算运算符方法的输出：");
		test.computeOperator();
		System.out.println("比较运算符方法的输出：");
		test.compareOperator();
		System.out.println("位运算符方法的输出：");
		test.bitOperator();
		System.out.println("布尔运算符方法的输出：");
		test.booleanOperator();
		System.out.println("赋值运算符方法的输出：");
		test.evaluateOperator();
		System.out.println("其他运算符方法的输出：");
		test.otherOperator();
	}
}
