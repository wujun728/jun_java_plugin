package exception;

/**
 * checked exception
 * Created by qianjia on 2017/1/22.
 */
public class FooException extends Exception {

  public FooException() {
    super();
  }

  public FooException(String message) {
    super(message);
  }

  public FooException(String message, Throwable cause) {
    super(message, cause);
  }

  public FooException(Throwable cause) {
    super(cause);
  }

  protected FooException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
