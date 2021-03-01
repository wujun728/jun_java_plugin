package book.exception;

/**
 * try catch finally的用法。
 * 无论是否发生异常，finally代码块始终都会执行
 */
public class Finally {

	/**
	 * 计算平方根
	 * @param nStr	数字的字符串
	 * @return
	 * @throws MyFirstException		当输入的字符串为空，或者字符串不能转化成数字时，抛出该异常
	 * @throws MySecondException	当输入的字符串转化成的数字为负数时，抛出该异常
	 */
	public static double getSqrt(String nStr) throws MyFirstException, MySecondException{
		if (nStr == null){
			throw new MyFirstException("输入的字符串不能为空！");
		}
		double n = 0;
		try {
			n = Double.parseDouble(nStr);
		} catch (NumberFormatException e){
			throw new MyFirstException("输入的字符串必须能够转化成数字！", e);
		}
		if (n < 0){
			throw new MySecondException("输入的字符串转化成的数字必须大于等于0！");
		}
		return Math.sqrt(n);
	}

	public static double testFinallyA(String nStr){
		try {
			System.out.println("Try block!");
			return getSqrt(nStr);
		} catch (MyFirstException e1){
			System.out.println("Catch MyFirstException block!");
			System.out.println("Exception: " + e1.getMessage());
			return -1;
		} catch (MySecondException e2){
			System.out.println("Catch MySecondException block!");
			System.out.println("Exception: " + e2.getMessage());
			return -2;
		} finally {
			System.out.println("Finally block!");
		}
	}
	
	public static double testFinallyB(String nStr){
		try {
			System.out.println("Try block!");
			return getSqrt(nStr);
		} catch (MyFirstException e1){
			System.out.println("Catch MyFirstException block!");
			System.out.println("Exception: " + e1.getMessage());
			return -1;
		} catch (MySecondException e2){
			System.out.println("Catch MySecondException block!");
			System.out.println("Exception: " + e2.getMessage());
			return -2;
		} finally {
			System.out.println("Finally block!");
			//增加一行，返回一个值。
			return 0;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("使用testFinallyA方法: ");
		System.out.println("没发生异常时的输出: ");
		System.out.println("result: " + Finally.testFinallyA("123.4"));
		System.out.println("发生异常时的输出: ");
		System.out.println("result: " + Finally.testFinallyA("dfdg"));
		System.out.println();
		System.out.println("使用testFinallyB方法: ");
		System.out.println("没发生异常时的输出: ");
		System.out.println("result: " + Finally.testFinallyB("123.4"));
		System.out.println("发生异常时的输出: ");
		System.out.println("result: " + Finally.testFinallyB("dfdg"));
		//在finally块中return或者throw都是不推荐的。因为finally中的代码一定会执行，如果执行这两种语句，将会将程序的控制权跳出该方法。
		//导致其他try和catch代码块中的返回值不生效。
	}
}
