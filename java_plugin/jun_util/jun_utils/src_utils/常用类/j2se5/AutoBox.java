package book.j2se5;
/**
 * 演示基本数据类型的自动拆箱和装箱
 * 装箱是指基本数据类型变成相应的对象；
 * 拆箱是指把数据对象变成基本数字。
 */
public class AutoBox {
	/**
	 * 整数类型的自动拆箱和装箱。
	 */
	public static void intAutoBox(){
		int i = 100;
		// 可以将基本数字类型赋给数字对象。
		// 在J2SE5.0之前，必须用iObj = new Integer(200);
		Integer iObj = 200;//将200装箱
		System.out.println("开始时：i = " + i + "; iObj = " + iObj);
		Integer tempObj = iObj;
		iObj = i;
		// 将数字对象赋给基本数字类型
		// 在J2SE5.0之前，必须用i = tempObj.intValue();
		i = tempObj;// 将对象拆箱
		System.out.println("将i与iObj的值互换后：" +
				"i = " + i + "; iObj = " + iObj);
		// 在表达式内可以自动拆箱和装箱
		iObj += i + tempObj;
		i *= iObj + tempObj;
		System.out.println("作加法和乘法运算后：" +
				"i = " + i + "; iObj = " + iObj);
	}
	/**
	 * boolean类型的自动拆箱与装箱
	 */
	public static void booleanAutoBox(){
		boolean b = false;
		Boolean bObj = true;// 装箱
		if (bObj){//拆箱
			System.out.println("bObj = " + true);
		}
		if (b || bObj){
			b = bObj;// 拆箱
			System.out.println("bObj = " + bObj + "; b = " + b);
		}
	}
	/**
	 * 字符类型的自动拆箱与装箱
	 */
	public static void charAutoBox(){
		char ch = 'A';
		Character chObj = 'B';// 装箱
		System.out.println("ch = " + ch + "; chObj = " + chObj);
		if (ch != chObj){// 拆箱
			ch = chObj;// 拆箱
			System.out.println("ch = " + ch + "; chObj = " + chObj);
		}
	}
	
	public static void main(String[] args) {
		intAutoBox();
		booleanAutoBox();
		charAutoBox();
		// 注意，支持基本数据类型的自动拆箱和装箱，
		// 但是不支持基本类型数组的自动拆箱和装箱
		int[] is = {12,34,56};
		//Integer[] iObjs = is;// error!!!
	}
}
