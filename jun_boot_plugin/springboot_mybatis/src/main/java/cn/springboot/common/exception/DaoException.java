package cn.springboot.common.exception;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -6068436839343976019L;

	public DaoException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DaoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}

}
