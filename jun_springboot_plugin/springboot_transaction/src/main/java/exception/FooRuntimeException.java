package exception;

/**
 * unchecked exception
 * Created by qianjia on 2017/1/22.
 */
public class FooRuntimeException extends RuntimeException {

  public FooRuntimeException() {
    super();
  }

  public FooRuntimeException(String message) {
    super(message);
  }

  public FooRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public FooRuntimeException(Throwable cause) {
    super(cause);
  }

  protected FooRuntimeException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
