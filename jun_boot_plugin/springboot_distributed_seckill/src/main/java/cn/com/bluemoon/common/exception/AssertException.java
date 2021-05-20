package cn.com.bluemoon.common.exception;


import java.util.logging.Level;

/**
 * 断言异常类
 * 
 * @author Wujun
 */
public class AssertException extends RuntimeException {
	/** */
	private static final long serialVersionUID = 1L;
	private int code = 1;
	private Level level;

	public AssertException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException(Level level, String message) {
		super(message);
		this.level = level;
	}

	public AssertException(Throwable cause) {
		super(cause);
	}

	public AssertException(int code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 *
	 * @return property value of code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Getter method for property <tt>level</tt>.
	 *
	 * @return property value of level
	 */
	public final Level getLevel() {
		return level;
	}

}
