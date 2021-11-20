package book.basic;

public class ChangeBasicType {

	/**
	 * 基本类型的自动提升
	 * 自动提升规则：
	 * 		1，所有的byte 型和short型的值被提升到 int 型
	 *		2，如果一个操作数是long 型，整个表达式将被提升到long 型
	 * 		3，如果一个操作数是float 型，整个表达式将被提升到float 型
	 * 		4，如果有一个操作数是double 型，计算结果就是double 型
	 */
	private void typeAutoUpgrade() {
		byte b = 44;
		char c = 'b';
		short s = 1024;
		int i = 40000;
		long l = 12463l;
		float f = 35.67f;
		double d = 3.1234d;
		//(f * b)时，b自动提升为float类型，(l * f)时，l自动提升为float，
		//(i / c)时，c自动提升为int型，(d * s)时，s自动提升为double
		//再相加时，中间结果都变为double类型。这里result只能声明为double类型。
		//result声明为其他类型会出错，除非，进行强制类型转换
		double result = (f * b) + +(l * f) + (i / c) - (d * s);
		System.out.print((f * b) + " + " + (l * f) + " + " + (i / c) + " - "
				+ (d * s));
		System.out.println(" = " + result);
	}

	/**
	 * 基本类型的自动转换
	 * 自动转换发生的条件是：（1）两种类型是兼容的；（2）目标类型的范围比源类型的范围大。
	 * 其他情况下，必须进行强制类型转换
	 */
	private void autoChange() {
		char c = 'a';
		byte b = 44;
		short s0 = b;
		int i0 = s0;
		int i1 = c;
		long l = i0;
		float f = l;
		double d = f;
		System.out.print("c = " + c + "; b = " + b + "; s0 = " + s0);
		System.out.print("; i0 = " + i0 + "; i1 = " + i1 + "; l = " + l);
		System.out.println("; f = " + f + "; d = " + d);

		//当float类型转换成double时，会出现不相等的情况，这是因为，float的范围是32位，而double的范围是64位，在进行类型转换时，
		//操作的实际上都是二进制，有些实数用二进制能够精确表示，而有些实数无论用多少位二进制都无法表示，跟有理数和无理数的概念一样。
		//对不能够精确表示的实数进行类型转换时，会出现不相等的情况。
		float fl = 1.7f;
		double dou = fl;
		System.out.println("fl = " + fl + "; dou = " + dou);//fl = 1.7; dou = 1.7000000476837158
		//但是当把一个数从一种类型转换成另外一种类型，再转换回来时，值还是一样。
		fl = (float)dou;
		System.out.println("fl = " + fl + "; dou = " + dou);
	}

	/**
	 * 强制类型转换
	 * 当两种类型不兼容，或者目标类型范围比源类型范围小时，必须进行强制类型转换。
	 * 具体转换时会进行 截断、取模 操作。
	 */
	private void forceChange() {
		double d = 123.456d;
		float f = (float) d;
		long l = (long) d;
		int i = (int) d;
		short s = (short) d;
		byte b = (byte) d;

		System.out.print("d = " + d + "; f = " + f + "; l = " + l);
		System.out.println("; i = " + i + "; s = " + s + "; b = " + b);

		d = 567.89d;
		//下面的转换首先进行截断操作，将d的值变为567，又因为567比byte的范围256还大，于是进行取模操作，
		//567对256取模后的值为55
		b = (byte) d;
		System.out.println("d = " + d + "; b = " + b);
	}

	public static void main(String[] args) {
		ChangeBasicType test = new ChangeBasicType();
		test.typeAutoUpgrade();
		test.autoChange();
		test.forceChange();
	}
//	fl = 1.7; dou = 1.7000000476837158
//	fl = 1.7; dou = 1.7000000476837158
}
