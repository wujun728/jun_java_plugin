package book.io.log;

/**
 * Log操作过程中的异常
 */
public class LogException extends Exception {
	public LogException() {
		super();
	}

	public LogException(String msg) {
		super(msg);
	}

	public LogException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LogException(Throwable cause) {
		super(cause);
	}
}
