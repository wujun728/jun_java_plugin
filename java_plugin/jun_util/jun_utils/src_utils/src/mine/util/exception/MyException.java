package mine.util.exception;

/**
 * 自定义异常类方法
 * 1、通过继承Throwable
 * 2、通过继承Exception
 * 
 * @author Touch
 */
public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException() {
		super();
	}

	public MyException(String message) {
		super(message);
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException(Throwable cause) {
		super(cause);
	}
}
