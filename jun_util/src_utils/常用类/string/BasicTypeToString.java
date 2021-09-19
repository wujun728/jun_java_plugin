package book.string;

/**
 * 基本类型与字符串的互相转换
 */
public class BasicTypeToString{
	/**
	 * 整数转换成字符串 
	 * @param n	待转换的整数
	 */
	public String int2str(int n){
		//4种方法实现转换。
		//return new Integer(n).toString();
		//return "" + n;
		//return Integer.toString(n);
		return String.valueOf(n);
	}
	/**
	 * 字符串转换成整数
	 * @param str	待转换的字符串
	 */
	public int str2int(String str){
		//2种方法实现转换
		//return new Integer(str).intValue();
		return Integer.parseInt(str);
	}
	public String double2str(double d){
		//return new Double(d).toString();
		//return "" + d;
		//return Double.toString(d);
		return String.valueOf(d);
		
	}
	public double str2double(String str){
		//return new Double(str).doubleValue();
		return Double.parseDouble(str);
	}
	//其他基本类型与字符串的互相转换这里不一一列出，方法都类似

	public static void main(String[] args) {
		BasicTypeToString test = new BasicTypeToString();
		int n = 156;
		String strI = test.int2str(n);
		System.out.println("test.int2str(n) = " + strI );
		System.out.println("test.str2int(strI) = " + test.str2int(strI));
		
		double d = 12.345;
		String strD = test.double2str(d);
		System.out.println("test.double2str(d) = " + strD );
		System.out.println("test.str2double(strD) = " + test.str2double(strD));
	}
}

