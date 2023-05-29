package book.exception;
/**
 *  抛出异常和声明异常
 *	throws用在方法声明中，用于声明该方法可能会抛出的异常，允许throws多个异常。
 *  throw用在方法体内，当方法在执行时遇到异常情况，可以将异常情况封装为异常对象，然后throw，即抛出异常
 */
public class ThrowAndThrows {
	/**
	 * 计算一个数字的平方根
	 * @param nStr	以字符串的形式提供数字
	 * @return
	 * @throws Exception	当用户输入的字符串为空，
	 * 			或者字符串无法转换成数字，或者转换成的数字小于0，都会抛出异常
	 */
	public static double sqrt(String nStr) throws Exception{
		if (nStr == null){
			//用throw关键字抛出异常，当异常被抛出时，程序会跳出该方法
			throw new Exception("输入的字符串不能为空！");
		}
		double n = 0;
		try {
			n = Double.parseDouble(nStr);
		} catch (NumberFormatException e){
			//将parseDouble方法可能抛出的异常NumberFormatException捕获，
			//然后将捕获的异常重新封装并抛出
			throw new Exception("输入的字符串必须能够转化成数字！", e);
		}
		if (n < 0){
			throw new Exception("输入的字符串转化成的数字必须大于等于0！");
		}
		//计算平方根
		return Math.sqrt(n);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//由于sqrt方法的声明中有throws 关键字，所以，在调用该方法时，
		//必须对throws后面声明的异常进行处置，处置方式有两种: 
		//（1）本方法不理会该异常，在本方法的声明中同样声明throws，将异常向外抛出。跟本例中的main方法一样。
		//（2）本方法处理该异常。在调用sqrt方法时，使用try...catch语句，将可能会出现异常的代码块catch，然后进行处置。
		
		try{
			ThrowAndThrows.sqrt("-124.56");
		} catch (Exception e){
			//将sqrt方法声明的可能抛出的Exception异常捕获。
			//打印捕获的异常的堆栈信息，从堆栈信息中可以发现异常发生的位置和原因。
			System.out.println("Got a Exception: " + e.getMessage());
			e.printStackTrace();
			//不做进一步处理，将异常向外抛出。
			throw e;
		}
		
		//将sqrt声明了可能会抛出的异常向外抛，必须在方法声明中使用throws。
		ThrowAndThrows.sqrt("-124.56");
	}
}
