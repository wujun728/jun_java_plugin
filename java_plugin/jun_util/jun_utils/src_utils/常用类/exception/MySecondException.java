package book.exception;

/**
 * 自定义异常类
 * 第二种定义方式：继承Throwable 类
 * @author new
 *
 */
public class MySecondException extends Throwable {

	public MySecondException() {
		super();
	}

	public MySecondException(String msg) {
		super(msg);
	}

	public MySecondException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MySecondException(Throwable cause) {
		super(cause);
	}
}
