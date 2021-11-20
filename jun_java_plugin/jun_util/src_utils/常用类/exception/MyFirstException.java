package book.exception;

/**
 * 自定义异常类。
 * 第一种定义方式，继承Exception类
 */
public class MyFirstException extends Exception {
	
	public MyFirstException() {
		super();
	}

	public MyFirstException(String msg) {
		super(msg);
	}

	public MyFirstException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MyFirstException(Throwable cause) {
		super(cause);
	}
	//自定义异常类的主要作用是区分异常发生的位置，当用户遇到异常时，
	//根据异常名就可以知道哪里有异常，根据异常提示信息进行修改。
}
