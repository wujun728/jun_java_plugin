package org.smartboot.shared;

import org.apache.logging.log4j.Level;

/**
 * 统一异常类
 * 
 * @author Wujun
 * @version DbApiException.java, v 0.1 2016年1月26日 下午4:01:51 Seer Exp.
 */
public class SmartException extends RuntimeException {
	/** */
	private static final long serialVersionUID = 1L;
	private int code = 1;
	private Level level;
	private String toast;

	public SmartException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public SmartException(String message) {
		super(message);
	}

	public SmartException(Level level, String message) {
		this(level, message, message);
	}

	public SmartException(Level level, String toast, String message) {
		super(message);
		this.level = level;
		this.toast = toast;
	}

	public SmartException(Throwable cause) {
		super(cause);
	}

	public SmartException(int code, String message) {
		this(code, message, message);
	}

	public SmartException(int code, String toast, String message) {
		super(message);
		this.code = code;
		this.toast = toast;
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

	public String getToast() {
		return toast;
	}

}
