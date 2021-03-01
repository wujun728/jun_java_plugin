package book.number;

/**
 * 数字的封装类
 * 为了满足用户可能会需要以对象的方式操作基本类型，因此，
 * Java为每种基本数据类型都定义了相应的封装类。
 * byte --> Byte; short --> Short; int --> Integer
 * long --> Long; float --> Float; double --> Double
 * boolean --> Boolean; char --> Character 
 */
public class NumberClass {
	
	/** 基本类型到封装类型的转换：以基本类型的数据为参数new一个相应封装类的对象
	 * 封装类型到基本类型的转换：返回封装类型对象的相应的value值。 */
	
	/**
	 * byte类型数字转换成Byte类型对象
	 */
	public static Byte byte2Byte(byte b){
		//return Byte.valueOf(b);
		return new Byte(b);
	}
	/**
	 * Byte类型对象转换成byte类型数字
	 */
	public static byte Byte2byte(Byte B){
		if (B == null) {
			return 0;
		} else {
			return B.byteValue();
		}
	}
	/**
	 * int类型数字转换成Integer类型对象
	 */
	public static Integer int2Integer(int i){
		// return Integer.valueOf(i);
		return new Integer(i);
	}
	/**
	 * Integer类型对象转换成int类型数字
	 */
	public static int Integer2int(Integer integer){
		if (integer == null) {
			return 0;
		} else {
			return integer.intValue();
		}
	}
	//其他基本类型与封装类型的相互转换都符合这个规则，这里就不一一列出了
	
	public static void main(String[] args) {
		int i = 5;
		Integer I = int2Integer(i);
		//将int类型转换成Integer之后，可以变成字符串
		String iStr = I.toString();//将int类型转换成Integer之后，可以变成字符串
		Integer a = new Integer(5);
		Integer b = new Integer(10);
		//Integer对象本身不能进行加减乘除的运算，必须使用它的int值进行运算
		int sum = a.intValue() + b.intValue();
	}
}
