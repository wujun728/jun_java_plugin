package cn.antcore.security.exception;

/**
 * 没有权限
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
public class NotAuthorizeException extends RuntimeException {
    public NotAuthorizeException(String message) {
        super(message);
    }
}
