package book.number;

/**
 * 描述数字的进制，有八进制、十进制和十六进制
 */
public class EnterSystem {

	public static void main(String[] args) {

		//八进制数字的声明，在前面加上0(零)
		int iOct = 0567;
		//十进制的声明
		int iTen = 1000;
		//十六进制数字的声明，在前面加上0x(零x)，x不区分大小写		
		int iHex = 0xABCD;

		//八进制转换成二进制
		System.out.print("八进制0567转换成二进制：");
		System.out.print(Integer.toString(iOct, 2) + "; ");//101110111
		System.out.println(Integer.toBinaryString(iOct));//101110111
		//八进制转换成十进制
		System.out.print("八进制0567转换成十进制：");
		System.out.print(Integer.toString(iOct, 10) + "; ");//375
		System.out.println(Integer.toString(iOct));
		//八进制转换成十六进制
		System.out.print("八进制0567转换成十六进制：");
		System.out.print(Integer.toString(iOct, 16) + "; ");//177
		System.out.println(Integer.toHexString(iOct));
		//还可以转换成其他进制
		System.out.print("八进制0567转换成七进制：");
		System.out.println(Integer.toString(iOct, 7));//1044

		//同样可以将十进制、十六进制转换成其他任意进制的数字
		System.out.print("十进制1000转换成十六进制：");
		System.out.print(Integer.toString(iTen, 16) + "; ");//3e8
		System.out.println(Integer.toHexString(iTen));
		System.out.print("十进制1000转换成八进制：");
		System.out.println(Integer.toOctalString(iTen));
		System.out.print("十六进制0xABCD转换成十进制：");
		System.out.println(Integer.toString(iHex, 10));//43981
		System.out.print("十六进制0xABCD转换成二进制：");
		System.out.print(Integer.toBinaryString(iHex) + "; ");
		System.out.println(Long.toBinaryString(iHex));

		//Java的整型封装类Integer和Long提供toString(int i, int radix)静态方法，
		//可以将一个任意进制的整数转换成其他任意进制的整数
		//除了上述方法之外，整数转换成二进制，可以使用toBinaryString(int i)方法
		//整数转换成八进制，可以使用toOctalString(int i)方法
		//整数转换成十六进制，可以使用toHexString(int i)方法。
		//这三个方法的最终实现都使用了toString(int i, int radix)。
	}
}
