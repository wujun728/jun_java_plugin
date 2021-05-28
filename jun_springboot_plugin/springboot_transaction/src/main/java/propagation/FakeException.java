package propagation;

/**
 * Created by qianjia on 2017/1/23.
 */
public class FakeException extends RuntimeException {

  public FakeException() {
    super();
  }

  public FakeException(String message) {
    super(message);
  }

  public FakeException(String message, Throwable cause) {
    super(message, cause);
  }

  public FakeException(Throwable cause) {
    super(cause);
  }

  protected FakeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
